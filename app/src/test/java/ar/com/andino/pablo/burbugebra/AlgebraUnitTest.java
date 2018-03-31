package ar.com.andino.pablo.burbugebra;

import org.junit.Test;

import ar.com.andino.pablo.burbugebra.elements.groupables.Factor;
import ar.com.andino.pablo.burbugebra.elements.no_grupables.GroupFactor;
import ar.com.andino.pablo.burbugebra.elements.no_grupables.GroupTerm;
import ar.com.andino.pablo.burbugebra.elements.groupables.Term;

public class AlgebraUnitTest {

    @Test
    public void getStringValue() throws Exception {

        // 2·(5+8·3)+5·8/3

        Factor f5 = new Factor(5,2);
        Factor f8_3 = new Factor(8, 3);

        GroupTerm groupTerm0 = new GroupTerm(
                new Term(
                        new GroupFactor(
                                new Factor(2),
                                new Factor(
                                        new GroupTerm(
                                                new Term(5),
                                                new Term(
                                                        new GroupFactor(
                                                                new Factor(8),
                                                                new Factor(-3)
                                                        )
                                                )
                                        )
                                )
                        )
                ),
                new Term(
                        new GroupFactor(f5, f8_3)
                )
        );

        System.out.println(groupTerm0.toString());

        f5.getValue().group(f8_3);

        System.out.println(groupTerm0.toString());

        //groupTerm0.distributive(new Rational(9));

        System.out.println(groupTerm0.toString());

    }

}