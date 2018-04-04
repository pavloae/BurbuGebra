package ar.com.andino.pablo.burbugebra;

import org.junit.Assert;
import org.junit.Test;

import ar.com.andino.pablo.burbugebra.elements.Equation;
import ar.com.andino.pablo.burbugebra.elements.groupables.Factor;
import ar.com.andino.pablo.burbugebra.elements.no_grupables.GroupFactor;
import ar.com.andino.pablo.burbugebra.elements.no_grupables.GroupTerm;
import ar.com.andino.pablo.burbugebra.elements.groupables.Term;
import ar.com.andino.pablo.burbugebra.elements.no_grupables.Rational;

public class AlgebraUnitTest {

    Equation equation;

    public void initEquation(){

        equation = new Equation();

        // 2·(5+8·(-3))+5/2·8/3

        equation.setLeftMember(
                new GroupTerm(
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
                                new GroupFactor(
                                        new Factor(5,2),
                                        new Factor(8, 3)
                                )
                        )
                )
        );

    }

    @Test
    public void getStringValue() throws Exception {


        initEquation();

        System.out.println(equation.toString());

        Factor f5_2 = ((GroupFactor) equation.getLeftMember().get(1).value).get(0);
        Factor f8_3 = ((GroupFactor) equation.getLeftMember().get(1).value).get(1);

        f5_2.group(f8_3);

        System.out.println(equation.toString());

        Factor f2 = ((GroupFactor) equation.getLeftMember().get(0).value).get(0);
        Factor fg = ((GroupFactor) equation.getLeftMember().get(0).value).get(1);

        f2.group(fg);

        System.out.println(equation.toString());

    }

    @Test
    public void compare() throws Exception {

        Factor factor1 = new Factor(3);
        Factor factor2 = new Factor(3);
        Factor factor3 = factor1;

        Assert.assertFalse(factor1.equals(factor2));
        Assert.assertFalse(factor1 == factor2);
        Assert.assertTrue(factor1.equals(factor3));
        Assert.assertTrue(factor1 == factor3);

        GroupFactor gf1 = new GroupFactor(factor1, factor2);
        GroupFactor gf2 = new GroupFactor(factor1, factor2);
        Term term = new Term(gf2);
        GroupFactor gf3 = gf1;
        GroupFactor gf4 = gf2.clone();

        Assert.assertTrue(gf1.equals(gf3));
        Assert.assertTrue(gf1 != gf2);
        Assert.assertTrue(gf1.equals(gf2));
        Assert.assertTrue(gf2 != gf4);
        Assert.assertTrue(gf2.equals(gf4));

    }

}