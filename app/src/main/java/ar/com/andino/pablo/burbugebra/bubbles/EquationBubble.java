package ar.com.andino.pablo.burbugebra.bubbles;

import android.graphics.Canvas;

public class EquationBubble extends TextBubble {

    private MemberBubble leftMember, rightMember;
    private OperatorBubble equalOperator;

    public EquationBubble() {
        super(null, 0,0,0);
        leftMember = new MemberBubble<TermBubble>();
        equalOperator = new OperatorBubble(OperatorBubble.EQUAL);
        rightMember = new MemberBubble<TermBubble>();
    }

    @Override
    public void onDraw(Canvas canvas) {
        leftMember.onDraw(canvas);
        equalOperator.onDraw(canvas);
        rightMember.onDraw(canvas);
    }

    @Override
    public void update() {
        leftMember.update();
        equalOperator.update();
        rightMember.update();
    }

}
