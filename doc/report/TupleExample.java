public class TupleExample {

    public static void main(String[] args) {

        // This code probably should be part of a suite of unit tests
        // instead of part of this a sample program

        final TupleType tripletTupleType =
            TupleType.DefaultFactory.create(
                    Number.class,
                    Number.class,
                    String.class,
                    Character.class);

        final Tuple t1 = tripletTupleType.createTuple(1,1, "one", 'a');
        final Tuple t2 = tripletTupleType.createTuple(1,2l, "two", 'b');
        final Tuple t3 = tripletTupleType.createTuple(1,3f, "three", 'c');
        final Tuple tnull = tripletTupleType.createTuple(null,null, "(null)", null);
        System.out.println("t1 = " + t1);
        System.out.println("t2 = " + t2);
        System.out.println("t3 = " + t3);
        System.out.println("tnull = " + tnull);

        final TupleType emptyTupleType =
            TupleType.DefaultFactory.create();

        final Tuple tempty = emptyTupleType.createTuple();
        System.out.println("\ntempty = " + tempty);


    }

}