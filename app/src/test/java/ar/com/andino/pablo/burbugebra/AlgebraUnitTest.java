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

    }

    @Test
    public void getStringValue() throws Exception {

        // 2·(5+8·3)+5·8/3

        Factor f2 = new Factor(2);
        Factor gf = new Factor(
                new GroupTerm(
                        new Term(5),
                        new Term(
                                new GroupFactor(
                                        new Factor(8),
                                        new Factor(-3)
                                )
                        )
                )
        );

        Factor f5_2 = new Factor(5,2);
        Factor f8_3 = new Factor(8, 3);

        GroupTerm groupTerm0 = new GroupTerm(
                new Term(
                        new GroupFactor(
                                f2,
                                gf
                        )
                ),
                new Term(
                        new GroupFactor(f5_2, f8_3)
                )
        );

        initEquation();

        equation.getLeftMember().addAll(groupTerm0);

        System.out.println(equation.toString());

        f5_2.group(f8_3);

        System.out.println(equation.toString());

        f2.group(gf);

        System.out.println(equation.toString());

        GroupFactor gF = (GroupFactor) groupTerm0.get(0).value;
        gF.get(0).group(gF.get(1));

        System.out.println(equation.toString());

        gF = (GroupFactor) groupTerm0.get(1).value;
        gF.get(0).group(gF.get(1));

        System.out.println(equation.toString());

        gF.get(0).group(gF.get(1));

        System.out.println(equation.toString());

        groupTerm0.get(0).group(groupTerm0.get(1));

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
        GroupFactor gf3 = gf1;
        GroupFactor gf4 = new GroupFactor(factor1, factor3);

        Assert.assertTrue(gf1.equals(gf3));
        Assert.assertTrue(gf1 == gf3);

    }

}