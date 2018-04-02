package ar.com.andino.pablo.burbugebra;

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

        Factor f5 = new Factor(5,2);
        Factor f8_3 = new Factor(8, 3);

        GroupTerm groupTerm0 = new GroupTerm(
                new Term(
                        new GroupFactor(
                                f2,
                                gf
                        )
                ),
                new Term(
                        new GroupFactor(f5, f8_3)
                )
        );

        initEquation();

        equation.getLeftMember().addAll(groupTerm0);

        System.out.println(equation.toString());

        f5.group(f8_3);

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

}