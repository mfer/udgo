  </simulation>
  <plugin>
    org.contikios.cooja.plugins.SimControl
    <width>280</width>
    <z>4</z>
    <height>160</height>
    <location_x>152</location_x>
    <location_y>595</location_y>
  </plugin>
  <plugin>
    org.contikios.cooja.plugins.Visualizer
    <plugin_config>
      <moterelations>true</moterelations>
      <skin>org.contikios.cooja.plugins.skins.IDVisualizerSkin</skin>
      <skin>org.contikios.cooja.plugins.skins.GridVisualizerSkin</skin>
      <skin>org.contikios.cooja.plugins.skins.TrafficVisualizerSkin</skin>
      <skin>org.contikios.udgo.UDGOVisualizerSkin</skin>
      <skin>org.contikios.cooja.plugins.skins.UDGMVisualizerSkin</skin>
      <viewport>15.18540864960725 0.0 0.0 15.18540864960725 110.10311787431888 94.08315879091218</viewport>
      <hidden />
    </plugin_config>
    <width>509</width>
    <z>5</z>
    <height>529</height>
    <location_x>139</location_x>
    <location_y>13</location_y>
  </plugin>
  <plugin>
    org.contikios.cooja.plugins.LogListener
    <plugin_config>
      <filter />
      <formatted_time />
      <coloring />
    </plugin_config>
    <width>248</width>
    <z>3</z>
    <height>796</height>
    <location_x>1550</location_x>
    <location_y>12</location_y>
  </plugin>
  <plugin>
    org.contikios.cooja.plugins.TimeLine
    <plugin_config>
      <mote>0</mote>
      <mote>1</mote>
      <mote>2</mote>
      <mote>3</mote>
      <mote>4</mote>
      <mote>5</mote>
      <mote>6</mote>
      <mote>7</mote>
      <mote>8</mote>
      <mote>9</mote>
      <mote>10</mote>
      <mote>11</mote>
      <mote>12</mote>
      <mote>13</mote>
      <mote>14</mote>
      <mote>15</mote>
      <showRadioRXTX />
      <showRadioHW />
      <showLEDs />
      <zoomfactor>500.0</zoomfactor>
    </plugin_config>
    <width>1920</width>
    <z>2</z>
    <height>166</height>
    <location_x>0</location_x>
    <location_y>832</location_y>
  </plugin>
  <plugin>
    org.contikios.udgo.AreaViewer
    <plugin_config>
      <selected mote="5" />
      <controls_visible>false</controls_visible>
      <zoom_x>12.653449466076644</zoom_x>
      <zoom_y>12.653449466076644</zoom_y>
      <pan_x>7.0670987351537855</pan_x>
      <pan_y>7.483451151258729</pan_y>
      <show_background>true</show_background>
      <show_obstacles>true</show_obstacles>
      <show_channel>true</show_channel>
      <show_radios>true</show_radios>
      <show_arrow>true</show_arrow>
      <vis_type>signalStrengthButton</vis_type>
      <resolution>100</resolution>
    </plugin_config>
    <width>500</width>
    <z>1</z>
    <height>500</height>
    <location_x>720</location_x>
    <location_y>20</location_y>
  </plugin>
  <plugin>
    org.contikios.cooja.plugins.ScriptRunner
    <plugin_config>
      <script>TIMEOUT(600000);&#xD;
&#xD;
while (true) {&#xD;
  log.log(time + ":" + id + ":" + msg + "\n");&#xD;
  YIELD();&#xD;
}</script>
      <active>false</active>
    </plugin_config>
    <width>639</width>
    <z>0</z>
    <height>221</height>
    <location_x>535</location_x>
    <location_y>571</location_y>
  </plugin>
</simconf>