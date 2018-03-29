package ar.com.andino.pablo.burbugebra;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import ar.com.andino.pablo.burbugebra.elementos.Factor;
import ar.com.andino.pablo.burbugebra.elementos.GroupFactor;
import ar.com.andino.pablo.burbugebra.elementos.GroupTerm;
import ar.com.andino.pablo.burbugebra.elementos.Rational;
import ar.com.andino.pablo.burbugebra.elementos.Term;

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

        f5.addFactor(f8_3);

        System.out.println(groupTerm0.toString());

        groupTerm0.distributive(new Rational(9));

        System.out.println(groupTerm0.toString());

    }

}