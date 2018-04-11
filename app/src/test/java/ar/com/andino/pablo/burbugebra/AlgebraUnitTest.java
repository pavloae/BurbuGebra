package ar.com.andino.pablo.burbugebra;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import ar.com.andino.pablo.burbugebra.elements.groupables.Equation;
import ar.com.andino.pablo.burbugebra.elements.groupables.Factor;
import ar.com.andino.pablo.burbugebra.elements.no_grupables.GroupFactor;
import ar.com.andino.pablo.burbugebra.elements.no_grupables.GroupTerm;
import ar.com.andino.pablo.burbugebra.elements.groupables.Term;
import ar.com.andino.pablo.burbugebra.elements.no_grupables.Rational;

public class AlgebraUnitTest {

    private Equation equation;

    private void initEquation(){

        equation = new Equation();

        // (-2)·(5+8·(-3))+5/2·8/3

        equation.setLeftMember(
                new GroupTerm(
                        new Term(
                                new GroupFactor(
                                        new Factor(-2),
                                        new Factor(
                                                new GroupTerm(
                                                        new Term(23),
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

        equation.setRightMember(
                new GroupTerm(
                        new Term(
                                new GroupFactor(
                                        new Factor(5,2),
                                        new Factor("X")
                                )
                        ),
                        new Term(
                                new GroupFactor(
                                        new Factor(8, 3),
                                        (Factor) new Factor(3, 5).toggleOperation()
                                )
                        ),
                        new Term(
                                new GroupFactor(
                                        new Factor(2, 3),
                                        new Factor("X")
                                )
                        )
                )
        );

    }

    @Test
    public void getStringValue() {

        long initTime = System.currentTimeMillis();

        initEquation();

        System.out.println(equation);

        ((GroupFactor) ((Term) equation.getLeftMember().get(0)).getValue()).get(1).invert();

        System.out.println(equation);

        Factor f5_2 = ((GroupFactor) ((Term) equation.getLeftMember().get(1)).value).get(0);
        Factor f8_3 = ((GroupFactor) ((Term) equation.getLeftMember().get(1)).value).get(1);

        f5_2.group(f8_3);

        System.out.println(equation);

        Factor f2 = ((GroupFactor) ((Term) equation.getLeftMember().get(0)).value).get(0);
        Factor fg = ((GroupFactor) ((Term) equation.getLeftMember().get(0)).value).get(1);

        f2.group(fg);

        System.out.println(equation);

        f2 = ((GroupFactor) ((Term) equation.getLeftMember().get(0)).value).get(0);
        Factor f5 = ((GroupFactor) ((Term) equation.getLeftMember().get(0)).value).get(1);

        f2.group(f5);

        System.out.println(equation);

        f2 = ((GroupFactor) ((Term) equation.getLeftMember().get(1)).value).get(0);
        Factor f8 = ((GroupFactor) ((Term) equation.getLeftMember().get(1)).value).get(1);

        f2.group(f8);

        System.out.println(equation);

        Factor f16 = ((GroupFactor) ((Term) equation.getLeftMember().get(1)).value).get(0);
        Factor f_3 = ((GroupFactor) ((Term) equation.getLeftMember().get(1)).value).get(1);

        if (f16.group(f_3))
            System.out.println(equation);

        Term t10 = (Term) equation.getLeftMember().get(0);
        Term t_48 = (Term) equation.getLeftMember().get(1);

        if (t10.group(t_48))
            System.out.println(equation);

        Term t_38 = (Term) equation.getLeftMember().get(0);
        Term t40_6 = (Term) equation.getLeftMember().get(1);

        t_38.group(t40_6);

        System.out.println(equation);

        if(equation.changeMember(equation.getLeftMember().get(0)))
            System.out.println(equation);

        ((Term) equation.getRightMember().get(3)).invert();

        System.out.println(equation);

        ((GroupFactor) ((Term) equation.getRightMember().get(1)).getValue()).get(0).group(
                ((GroupFactor) ((Term) equation.getRightMember().get(1)).getValue()).get(1)
        );

        System.out.println(equation);

        ((Term) equation.getRightMember().get(1)).group(
                equation.getRightMember().get(3)
        );

        System.out.println(equation);

        ((Rational) ((Term) equation.getRightMember().get(1)).getValue()).simplify();

        System.out.println(equation);

        ((Term) equation.getRightMember().get(1)).invert();

        System.out.println(equation);

        ((GroupTerm) equation.getRightMember()).add(
                (Term) equation.getRightMember().remove(1)
        );

        System.out.println(equation);

        boolean result = ((GroupTerm) equation.getRightMember()).get(0).group(
                ((GroupTerm) equation.getRightMember()).get(1)
        );

        System.out.println(equation + " -> " + result);

        result = ((GroupTerm) equation.getRightMember()).get(0).group(
                ((GroupTerm) equation.getRightMember()).get(1)
        );

        System.out.println(equation + " -> " + result);

        System.out.println("Time: " + (System.currentTimeMillis() - initTime) + " ms");

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

    @Test
    public void addToList() throws Exception {

        List<String> arrayList = new ArrayList<>();

        System.out.println(arrayList.add("x"));
        System.out.println(arrayList.size());
        System.out.println(arrayList.add(null));
        System.out.println(arrayList.size());

        System.out.println(12/6/2);


    }

    @Test
    public void cloneElement() throws Exception {

        Rational rational1 = new Rational(2, 3);

        Term term1 = new Term(rational1);

        Assert.assertTrue(rational1.getParent() == term1);

        Term term2 = new Term((Rational) rational1.clone());

        Assert.assertTrue(term2.value != rational1);

        Assert.assertTrue(((Rational) term2.value).getParent() != rational1.getParent());

        GroupTerm groupTerm = new GroupTerm(term1);
        groupTerm.add((Term) term1.clone());

        Assert.assertTrue(groupTerm.size()==2);

        GroupTerm clone = groupTerm.clone();

        Assert.assertTrue(groupTerm.get(0) == clone.get(0));

    }

    @Test
    public void xorTest() throws Exception {

        System.out.println(true ^ false);
        System.out.println(true ^ true);
        System.out.println(false ^ false);
        System.out.println(false ^ true);


    }


}