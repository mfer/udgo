/*
 * Copyright (c) 2006, Swedish Institute of Computer Science.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. Neither the name of the Institute nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE INSTITUTE AND CONTRIBUTORS ``AS IS'' AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED.  IN NO EVENT SHALL THE INSTITUTE OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS
 * OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 *
 */

package org.contikios.udgo;

import org.apache.log4j.Logger;
import org.jdom.Element;
import java.util.Observer;
import java.util.Observable;
import java.util.Hashtable;
import java.util.Random;
import java.util.Collection;
import org.contikios.cooja.ClassDescription;
import org.contikios.cooja.interfaces.Position;
import org.contikios.cooja.interfaces.Radio;
import org.contikios.cooja.plugins.Visualizer;
import org.contikios.cooja.radiomediums.UDGM;
import org.contikios.cooja.radiomediums.DestinationRadio;
import org.contikios.cooja.RadioConnection;
import org.contikios.cooja.Simulation;
import org.contikios.udgo.ChannelModel.Parameter;
import org.contikios.udgo.ChannelModel.RadioPair;
import org.contikios.udgo.ChannelModel.TxPair;
import org.contikios.cooja.interfaces.NoiseSourceRadio;
import org.contikios.cooja.interfaces.NoiseSourceRadio.NoiseLevelListener;


/**
 * Unit Disk Graph Obstructed (UDGO).
 *
 * UDGO is an implementation of the models presented in
 * Marcelo G. Almiron, Olga Goussevskaia, Antonio A.F. Loureiro, and Jose Rolim. 2013. 
 * Connectivity in Obstructed Wireless Networks: From Geometry to Percolation. 
 * In Proceedings of the fourteenth ACM international symposium on Mobile ad hoc networking 
 * and computing (MobiHoc '13). ACM, New York, NY, USA, 157-166.
 * @see DirectionalAntennaRadio
 * @see NoiseSourceRadio
 * @author Manassés Ferreira Neto
 * This file was greatly inspired/based in MRM.java of author Fredrik Osterlind
 * link: https://github.com/contiki-os/contiki/blob/master/tools/cooja/apps/mrm/java/org/contikios/mrm/MRM.java
 * More at
 *   Osterlind, F.; Dunkels, A.; Eriksson, J.; Finne, N.; Voigt, T., 
 *   "Cross-Level Sensor Network Simulation with COOJA," Local Computer Networks, Proceedings 2006 31st IEEE 
 *   Conference on , vol., no., pp.641,648, 14-16 Nov. 2006 doi: 10.1109/LCN.2006.322172 
 *   keywords: {digital simulation;telecommunication computing;wireless sensor networks;COOJA simulator;
 *   Contiki sensor node operating system;cross-level sensor network simulation;holistic simultaneous simulation;
 *   wireless sensor network simulation;Computational modeling;Computer science;Computer simulation;Hardware;Java;
 *   Operating systems;Programming;Sensor systems;Software systems;Wireless sensor networks},
 *   URL: http://ieeexplore.ieee.org/stamp/stamp.jsp?tp=&arnumber=4116633&isnumber=4116490
 */
@ClassDescription("UnitDiskGraphObstructed (udgo)")
public class UDGO extends UDGM {
  private static Logger logger = Logger.getLogger(UDGO.class);

  public final static boolean WITH_NOISE = true; /* NoiseSourceRadio */
  public final static boolean WITH_DIRECTIONAL = true; /* DirectionalAntennaRadio */
  private Observer channelModelObserver = null;
  private boolean WITH_CAPTURE_EFFECT;
  private double CAPTURE_EFFECT_THRESHOLD;
  private double CAPTURE_EFFECT_PREAMBLE_DURATION;

  private Simulation sim;
  private Random random = null;
  private ChannelModel currentChannelModel = null;
  private SettingsObservable settingsObservable = new SettingsObservable();

  public UDGO(Simulation simulation) {
    super(simulation);
    sim = simulation;
    random = simulation.getRandomGenerator();

    currentChannelModel = new ChannelModel(sim);

    WITH_CAPTURE_EFFECT = currentChannelModel.getParameterBooleanValue(ChannelModel.Parameter.captureEffect);
    CAPTURE_EFFECT_THRESHOLD = currentChannelModel.getParameterDoubleValue(ChannelModel.Parameter.captureEffectSignalTreshold);
    CAPTURE_EFFECT_PREAMBLE_DURATION = currentChannelModel.getParameterDoubleValue(ChannelModel.Parameter.captureEffectPreambleDuration);
   
    currentChannelModel.addSettingsObserver(channelModelObserver = new Observer() {
      public void update(Observable o, Object arg) {
        WITH_CAPTURE_EFFECT = currentChannelModel.getParameterBooleanValue(ChannelModel.Parameter.captureEffect);
        CAPTURE_EFFECT_THRESHOLD = currentChannelModel.getParameterDoubleValue(ChannelModel.Parameter.captureEffectSignalTreshold);
        CAPTURE_EFFECT_PREAMBLE_DURATION = currentChannelModel.getParameterDoubleValue(ChannelModel.Parameter.captureEffectPreambleDuration);
      }
    });

    currentChannelModel = new ChannelModel(sim);

    setTxRange(100);
    setInterferenceRange(0);

    sim.getCooja().registerPlugin(AreaViewer.class);
    sim.getCooja().registerPlugin(FormulaViewer.class);
    Visualizer.registerVisualizerSkin(UDGOVisualizerSkin.class);
  }

  public void removed() {
    super.removed();
    sim.getCooja().unregisterPlugin(AreaViewer.class);
    sim.getCooja().unregisterPlugin(FormulaViewer.class);
    Visualizer.unregisterVisualizerSkin(UDGOVisualizerSkin.class);
    currentChannelModel.deleteSettingsObserver(channelModelObserver);
  }

  private NoiseLevelListener noiseListener = new NoiseLevelListener() {
        public void noiseLevelChanged(NoiseSourceRadio radio, int signal) {
                updateSignalStrengths();
        };
  };
  public void registerRadioInterface(Radio radio, Simulation sim) {
        super.registerRadioInterface(radio, sim);
        
        if (WITH_NOISE && radio instanceof NoiseSourceRadio) {
                ((NoiseSourceRadio)radio).addNoiseLevelListener(noiseListener);
        }
  }
  public void unregisterRadioInterface(Radio radio, Simulation sim) {
        super.unregisterRadioInterface(radio, sim);

        if (WITH_NOISE && radio instanceof NoiseSourceRadio) {
                ((NoiseSourceRadio)radio).removeNoiseLevelListener(noiseListener);
        }
  }
  
  public UDGORadioConnection createConnections(final Radio sender) { 
    UDGORadioConnection newConnection = new UDGORadioConnection(sender);
    final Position senderPos = sender.getPosition();

    /* Calculate ranges: grows with radio output power */
    double moteTransmissionRange = TRANSMITTING_RANGE
    * ((double) sender.getCurrentOutputPowerIndicator() / (double) sender.getOutputPowerIndicatorMax());
    double moteInterferenceRange = INTERFERENCE_RANGE
    * ((double) sender.getCurrentOutputPowerIndicator() / (double) sender.getOutputPowerIndicatorMax());

//    System.out.println(moteTransmissionRange +" = "+ TRANSMITTING_RANGE);
//    System.out.println(moteInterferenceRange +" = "+ INTERFERENCE_RANGE);

    /* Loop through all potential destinations */
    for (Radio recv: getRegisteredRadios()) {

      //Radio recv = dest.radio;
      if (sender == recv) {
        continue;
      }

      /* Fail if radios are on different (but configured) channels */ 
      if (sender.getChannel() >= 0 &&
          recv.getChannel() >= 0 &&
          sender.getChannel() != recv.getChannel()) {
        continue;
      }

      final Radio recvFinal = recv;
      
      /* Calculate receive probability */
      TxPair txPair = new RadioPair() {
        public Radio getFromRadio() {
          return sender;
        }
        public Radio getToRadio() {
          return recvFinal;
        }
      };
      double[] probData = currentChannelModel.getProbability(
          txPair,
          -Double.MAX_VALUE
      );
      double recvProb = probData[0];
      double recvSignalStrength = probData[1];      
      Position recvPos = recv.getPosition();

      double distance = senderPos.getDistanceTo(recvPos);



//      System.out.println(senderPos+" --> "+recvPos);
      if (distance <= moteTransmissionRange) {
//        System.out.println("distance <= moteTransmissionRange");
        if (recvProb == 1.0 || random.nextDouble() < recvProb) {
//          System.out.println("recvProb == 1.0 || random.nextDouble() < recvProb");
          if (!recv.isRadioOn()) {
//            System.out.println("!recv.isRadioOn()");
            newConnection.addInterfered(recv);
            recv.interfereAnyReception();
          } else if (recv.isInterfered()) {
//            System.out.println("recv.isInterfered()");
            newConnection.addInterfered(recv, recvSignalStrength);
          } else if (recv.isTransmitting()) {
//            System.out.println("recv.isTransmitting()");
            newConnection.addInterfered(recv, recvSignalStrength);
          } else if (recv.isReceiving()) {
//            System.out.println("recv.isReceiving()");
            if (!WITH_CAPTURE_EFFECT) {
              newConnection.addInterfered(recv, recvSignalStrength);
              recv.interfereAnyReception();
              for (RadioConnection conn : getActiveConnections()) {
                if (conn.isDestination(recv)) {
                  conn.addInterfered(recv);
                }
              }
            } else {
              double currSignal = recv.getCurrentSignalStrength();
              if (recvSignalStrength >= currSignal - CAPTURE_EFFECT_THRESHOLD) {
                long startTime = newConnection.getReceptionStartTime();
                boolean interfering = (sim.getSimulationTime()-startTime) >= CAPTURE_EFFECT_PREAMBLE_DURATION; 
                if (interfering) {
                  newConnection.addInterfered(recv, recvSignalStrength);
                  recv.interfereAnyReception();
                  for (RadioConnection conn : getActiveConnections()) {
                    if (conn.isDestination(recv)) {
                      conn.addInterfered(recv);
                    }
                  }
                } else {
                  for (RadioConnection conn : getActiveConnections()) {
                    if (conn.isDestination(recv)) {
                      conn.removeDestination(recv);
                    }
                  }
//                  System.out.println("newConnection.addDestination(recv, recvSignalStrength) "+ distance+" "+" "+recvSignalStrength);                  
                  newConnection.addDestination(recv, recvSignalStrength);                  
                }
              }
            }
          } else {
//            System.out.println("newConnection.addDestination(recv, recvSignalStrength) "+ distance+" "+" "+recvSignalStrength);
            newConnection.addDestination(recv, recvSignalStrength);
          }
        }
      } else if (distance <= moteInterferenceRange) {
//        System.out.println("distance <= moteInterferenceRange");
        newConnection.addInterfered(recv);
        recv.interfereAnyReception();
      }

    }

    return newConnection;
  }



  public void updateSignalStrengths() {

    /* Reset: Background noise */
        double background =
                currentChannelModel.getParameterDoubleValue((Parameter.bg_noise_mean));
    for (Radio radio : getRegisteredRadios()) {
      radio.setCurrentSignalStrength(background);
    }

    /* Active radio connections */
    RadioConnection[] conns = getActiveConnections();
    for (RadioConnection conn : conns) {
      for (Radio dstRadio : ((UDGORadioConnection) conn).getDestinations()) {
        double signalStrength = ((UDGORadioConnection) conn).getDestinationSignalStrength(dstRadio);
        if (conn.getSource().getChannel() >= 0 &&
            dstRadio.getChannel() >= 0 &&
            conn.getSource().getChannel() != dstRadio.getChannel()) {
          continue;
        }
        if (dstRadio.getCurrentSignalStrength() < signalStrength) {
          dstRadio.setCurrentSignalStrength(signalStrength);
        }
      }
    }

    /* Interfering/colliding radio connections */
    for (RadioConnection conn : conns) {
      for (Radio intfRadio : ((UDGORadioConnection) conn).getInterfered()) {
        double signalStrength = ((UDGORadioConnection) conn).getInterferenceSignalStrength(intfRadio);
        if (intfRadio.getCurrentSignalStrength() < signalStrength) {
                intfRadio.setCurrentSignalStrength(signalStrength);
        }
        if (conn.getSource().getChannel() >= 0 &&
            intfRadio.getChannel() >= 0 &&
            conn.getSource().getChannel() != intfRadio.getChannel()) {
          continue;
        }

        if (!intfRadio.isInterfered()) {
          /*logger.warn("Radio was not interfered: " + intfRadio);*/
                intfRadio.interfereAnyReception();
        }
      }
    }

    /* Check for noise sources */
    if (!WITH_NOISE) return;
    for (Radio noiseRadio: getRegisteredRadios()) {
      if (!(noiseRadio instanceof NoiseSourceRadio)) {
        continue;
      }
      final Radio fromRadio = noiseRadio;
      NoiseSourceRadio radio = (NoiseSourceRadio) noiseRadio;
      int signalStrength = radio.getNoiseLevel();
      if (signalStrength == Integer.MIN_VALUE) {
        continue;
      }

      /* Calculate how noise source affects surrounding radios */
      for (Radio affectedRadio : getRegisteredRadios()) {
        if (noiseRadio == affectedRadio) {
          continue;
        }

        /* Update noise levels */
        final Radio toRadio = affectedRadio;
        TxPair txPair = new RadioPair() {
          public Radio getFromRadio() {
            return fromRadio;
          }
          public Radio getToRadio() {
            return toRadio;
          }
        };
        double[] signalMeanVar = currentChannelModel.getReceivedSignalStrength(txPair);
        double signal = signalMeanVar[0];
        if (signal < background) {
          continue;
        }

        /* TODO Additive signals strengths? */
        /* TODO XXX Consider radio channels */
        /* TODO XXX Potentially interfere even when signal is weaker (~3dB)...
* (we may alternatively just use the getSINR method...) */
        if (affectedRadio.getCurrentSignalStrength() < signal) {
          affectedRadio.setCurrentSignalStrength(signal);

          /* TODO Interfere with radio connections? */
          if (affectedRadio.isReceiving() && !affectedRadio.isInterfered()) {
            for (RadioConnection conn : conns) {
              if (conn.isDestination(affectedRadio)) {
                /* Intefere with current reception, mark radio as interfered */
                conn.addInterfered(affectedRadio);
                if (!affectedRadio.isInterfered()) {
                  affectedRadio.interfereAnyReception();
                }
              }
            }
          }
        }
      }
    }
  }

  public Collection<Element> getConfigXML() {
    return currentChannelModel.getConfigXML();
  }

  public boolean setConfigXML(Collection<Element> configXML,
      boolean visAvailable) {
    return currentChannelModel.setConfigXML(configXML);
  }


  // // -- udgo specific methods --

  /**
   * Adds an observer which is notified when this radio medium has
   * changed settings, such as added or removed radios.
   *
   * @param obs New observer
   */
  public void addSettingsObserver(Observer obs) {
    settingsObservable.addObserver(obs);
  }

  /**
   * Deletes an earlier registered setting observer.
   *
   * @param obs Earlier registered observer
   */
  public void deleteSettingsObserver(Observer obs) {
    settingsObservable.deleteObserver(obs);
  }

  /**
   * @return Number of registered radios.
   */
  public int getRegisteredRadioCount() {
        /* TODO Expensive operation */
    return getRegisteredRadios().length;
  }

  /**
   * Returns radio at given index.
   *
   * @param index Index of registered radio.
   * @return Radio at given index
   */
  public Radio getRegisteredRadio(int index) {
    return getRegisteredRadios()[index];
  }

  /**
   * Returns the current channel model object, responsible for
   * all probability and transmission calculations.
   *
   * @return Current channel model
   */
  public ChannelModel getChannelModel() {
    return currentChannelModel;
  }

  class SettingsObservable extends Observable {
    private void notifySettingsChanged() {
      setChanged();
      notifyObservers();
    }
  }

  class UDGORadioConnection extends RadioConnection {
    private Hashtable<Radio, Double> signalStrengths = new Hashtable<Radio, Double>();

    public UDGORadioConnection(Radio sourceRadio) {
      super(sourceRadio);
    }

    public void addDestination(Radio radio, double signalStrength) {
      signalStrengths.put(radio, signalStrength);
      addDestination(radio);
    }

    public void addInterfered(Radio radio, double signalStrength) {
      signalStrengths.put(radio, signalStrength);
      addInterfered(radio);
    }

    public double getDestinationSignalStrength(Radio radio) {
        if (signalStrengths.get(radio) == null) {
                return Double.MIN_VALUE;
        }
      return signalStrengths.get(radio);
    }

    public double getInterferenceSignalStrength(Radio radio) {
        if (signalStrengths.get(radio) == null) {
                return Double.MIN_VALUE;
        }
      return signalStrengths.get(radio);
    }
  }
}
