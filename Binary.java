import java.util.*;

import java.util.Vector;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import org.objectweb.asm.Label;

public class Binary extends Expr {

    public String operator;
    public Expr exprLeft;
    public Expr exprRight;
    public Type type;


    public static Map<String, List<Type>> VALID_OPERATORS = new HashMap<>() {{
        put("+", Arrays.asList(Type.TYPE_INT, Type.TYPE_CHAR));
        put("-", Arrays.asList(Type.TYPE_INT, Type.TYPE_CHAR));
        put("/", Arrays.asList(Type.TYPE_INT, Type.TYPE_CHAR));
        put("*", Arrays.asList(Type.TYPE_INT, Type.TYPE_CHAR));
        put("%", Arrays.asList(Type.TYPE_INT, Type.TYPE_CHAR));
        put("<", Arrays.asList(Type.TYPE_INT, Type.TYPE_CHAR));
        put("<=", Arrays.asList(Type.TYPE_INT, Type.TYPE_CHAR));
        put(">", Arrays.asList(Type.TYPE_INT, Type.TYPE_CHAR));
        put(">=", Arrays.asList(Type.TYPE_INT, Type.TYPE_CHAR));
        put("&&", Collections.singletonList(Type.TYPE_BOOL));
        put("||", Collections.singletonList(Type.TYPE_BOOL));
    }};

    public static List<String> SPECIAL_OPERATORS = Arrays.asList("==", "!=");

    public static Map<String, Type> OPERATOR_RETURN_TYPE = new HashMap<>() {{
        put("+", Type.TYPE_INT);
        put("-", Type.TYPE_INT);
        put("/", Type.TYPE_INT);
        put("*", Type.TYPE_INT);
        put("%", Type.TYPE_INT);
        put("<", Type.TYPE_BOOL);
        put("<=", Type.TYPE_BOOL);
        put(">", Type.TYPE_BOOL);
        put(">=", Type.TYPE_BOOL);
        put("&&", Type.TYPE_BOOL);
        put("||", Type.TYPE_BOOL);
    }};

    public Binary(final String operator, final Expr exprLeft, final Expr exprRight) {
        this.operator = operator;
        this.exprLeft = exprLeft;
        this.exprRight = exprRight;
    }

    public void codeGen(ClassWriter cw, MethodVisitor method, Class i_class, Vector<LocalVarDecl> localVar,
                        Stmt failedTask, Stmt successTask) {
        int selectedCode = 0;

        switch (operator) {
            case "==":
                selectedCode = Opcodes.IF_ICMPNE;
                break;
            case "!=":
                selectedCode = Opcodes.IF_ICMPEQ;
                break;
            case ">":
                selectedCode = Opcodes.IF_ICMPLT;
                break;
            case ">=":
                selectedCode = Opcodes.IF_ICMPLE;
                break;
            case "<":
                selectedCode = Opcodes.IF_ICMPGT;
                break;
            case "<=":
                selectedCode = Opcodes.IF_ICMPGE;
                break;
        }

        if (selectedCode != 0) {
            System.out.println("[Binary] Selected Operation: " + operator + "\nBetween:" + exprLeft.toString() + ", " + exprRight.toString());
            this.exprLeft.codeGen(cw, method, i_class, localVar);
            this.exprRight.codeGen(cw, method, i_class, localVar);
            Label successLabel = new Label();
            Label failedLabel = new Label();

            method.visitJumpInsn(selectedCode, failedLabel);
            method.visitJumpInsn(Opcodes.GOTO, successLabel);

            method.visitLabel(successLabel);
            successTask.codeGen(cw, method, i_class, localVar);

            method.visitLabel(failedLabel);
            failedTask.codeGen(cw, method, i_class, localVar);
            return;
        }

        switch (operator) {
            case "&&":
                selectedCode = Opcodes.IFEQ;
                break;
            case "||":
                selectedCode = Opcodes.IFNE;
                break;
        }

        if (selectedCode != 0) {
            System.out.println("[Binary] Selected Operation: " + operator + "\nBetween:" + exprLeft.toString() + ", " + exprRight.toString());
            this.exprLeft.codeGen(cw, method, i_class, localVar);

            Label successLabel = new Label();
            Label deciderLabel = new Label();
            Label failedLabel = new Label();

            method.visitJumpInsn(selectedCode, failedLabel);
            this.exprRight.codeGen(cw, method, i_class, localVar);

            if (selectedCode == Opcodes.IFEQ) {
                method.visitJumpInsn(selectedCode, failedLabel);

                method.visitLabel(successLabel);
                successTask.codeGen(cw, method, i_class, localVar);

                method.visitJumpInsn(Opcodes.GOTO, deciderLabel);

                method.visitLabel(failedLabel);
                method.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
                failedTask.codeGen(cw, method, i_class, localVar);

                method.visitLabel(deciderLabel);
                method.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
            } else {
                method.visitJumpInsn(Opcodes.IFEQ, successLabel);

                method.visitLabel(failedLabel);
                method.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
                failedTask.codeGen(cw, method, i_class, localVar);

                method.visitJumpInsn(Opcodes.GOTO, deciderLabel);

                method.visitLabel(successLabel);
                method.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
                successTask.codeGen(cw, method, i_class, localVar);

                method.visitLabel(deciderLabel);
                method.visitFrame(Opcodes.F_SAME1, 0, null, 1, null);
            }
            return;
        }

        switch (operator) {
            case "+":
                selectedCode = Opcodes.IADD;
                break;
            case "-":
                selectedCode = Opcodes.ISUB;
                break;
            case "*":
                selectedCode = Opcodes.IMUL;
                break;
            case "/":
                selectedCode = Opcodes.IDIV;
                break;
            case "%":
                selectedCode = Opcodes.IREM;
                break;
        }

        if (selectedCode != 0) {
            System.out.println("[Binary] Selected Operation: " + operator + "\nBetween:" + exprLeft.toString() + ", " + exprRight.toString());
            this.exprLeft.codeGen(cw, method, i_class, localVar);
            this.exprRight.codeGen(cw, method, i_class, localVar);
            method.visitInsn(selectedCode);
            return;
        }

        System.out.println("ERROR: No Operator selected");

    }


    public void codeGenWhile(ClassWriter cw, MethodVisitor method, Class i_class, Vector<LocalVarDecl> localVar, Stmt run) {
        int selectedCode = 0;

        switch (operator) {
            case "==":
                selectedCode = Opcodes.IF_ICMPNE;
                break;
            case "!=":
                selectedCode = Opcodes.IF_ICMPEQ;
                break;
            case ">":
                selectedCode = Opcodes.IF_ICMPLT;
                break;
            case ">=":
                selectedCode = Opcodes.IF_ICMPLE;
                break;
            case "<":
                selectedCode = Opcodes.IF_ICMPGT;
                break;
            case "<=":
                selectedCode = Opcodes.IF_ICMPGE;
                break;
        }

        if (selectedCode != 0) {
            System.out.println("[Binary] Selected Operation: " + operator + "\nBetween:" + exprLeft.toString() + ", " + exprRight.toString());
            Label initLabel = new Label();
            Label successLabel = new Label();
            Label failedLabel = new Label();

            method.visitLabel(initLabel);
            this.exprLeft.codeGen(cw, method, i_class, localVar);
            this.exprRight.codeGen(cw, method, i_class, localVar);


            method.visitJumpInsn(selectedCode, failedLabel);
            method.visitJumpInsn(Opcodes.GOTO, successLabel);

            method.visitLabel(successLabel);
            run.codeGen(cw, method, i_class, localVar);
            method.visitJumpInsn(Opcodes.GOTO, initLabel);
            return;
        }

        switch (operator) {
            case "&&":
                selectedCode = Opcodes.IFEQ;
                break;
            case "||":
                selectedCode = Opcodes.IFNE;
                break;
        }

        if (selectedCode != 0) {
            System.out.println("[Binary] Selected Operation: " + operator + "\nBetween:" + exprLeft.toString() + ", " + exprRight.toString());
            Label initLabel = new Label();
            Label successLabel = new Label();
            Label failedLabel = new Label();

            method.visitLabel(initLabel);
            this.exprLeft.codeGen(cw, method, i_class, localVar);

            method.visitJumpInsn(selectedCode, failedLabel);
            this.exprRight.codeGen(cw, method, i_class, localVar);

            if (selectedCode == Opcodes.IFEQ) {
                method.visitJumpInsn(selectedCode, failedLabel);

                method.visitLabel(successLabel);
                run.codeGen(cw, method, i_class, localVar);
                method.visitJumpInsn(Opcodes.GOTO, initLabel);

            } else {
                method.visitJumpInsn(Opcodes.IFEQ, successLabel);

                method.visitLabel(successLabel);
                run.codeGen(cw, method, i_class, localVar);
                method.visitJumpInsn(Opcodes.GOTO, initLabel);
            }
            return;
        }

        switch (operator) {
            case "+":
                selectedCode = Opcodes.IADD;
                break;
            case "-":
                selectedCode = Opcodes.ISUB;
                break;
            case "*":
                selectedCode = Opcodes.IMUL;
                break;
            case "/":
                selectedCode = Opcodes.IDIV;
                break;
            case "%":
                selectedCode = Opcodes.IREM;
                break;
        }

        if (selectedCode != 0) {
            System.out.println("[Binary]: Invalid Binary Operators for While");
            this.exprLeft.codeGen(cw, method, i_class, localVar);
            this.exprRight.codeGen(cw, method, i_class, localVar);
            method.visitInsn(selectedCode);
            return;
        }

        System.out.println("[Binary]: ERROR NO OPERATOR SET");

    }

    @Override
    public String toString() {
        return "Binary{" +
                "operator='" + this.operator + '\'' +
                ", exprLeft=" + this.exprLeft +
                ", exprRight=" + this.exprRight +
                '}';
    }

    @Override
    public Type typeCheck(Map<String, Type> localVars, Class thisClass) {
        Type exprLeftType = exprLeft.typeCheck(localVars, thisClass);
        Type exprRightType = exprRight.typeCheck(localVars, thisClass);
        if (VALID_OPERATORS.containsKey(operator)) {
            if (exprLeftType.equals(exprRightType)) {
                if (VALID_OPERATORS.get(operator).contains(exprLeftType)) {
                    type = OPERATOR_RETURN_TYPE.get(operator);
                    return type;
                } else {
                    throw new UnexpectedTypeException(String.format("Binary-Error: The Operator %s does not support Expressions of Type %s", operator, exprLeftType));
                }
            } else {
                throw new UnexpectedTypeException(String.format("Binary-Error: The Type %s of the Left-Expression %s does not match the Type %s of the Right-Expression %s", exprLeftType, exprLeft, exprRightType, exprRight));
            }
        } else if (SPECIAL_OPERATORS.contains(operator)) {
            if (!(exprLeftType.equals(Type.TYPE_VOID) || exprRightType.equals(Type.TYPE_VOID))) {
                if (exprLeftType.equals(exprRightType) || exprLeftType.equals(Type.TYPE_NULL) || exprRightType.equals(Type.TYPE_NULL)) {
                    type = Type.TYPE_BOOL;
                    return type;
                } else {
                    throw new UnexpectedTypeException(String.format("Binary-Error: The Left-Expression %s of Type %s does not have the same Type as the Right-Expression %s of Type %s", exprLeft, exprLeftType, exprRight, exprRightType));
                }
            } else {
                throw new UnexpectedTypeException("Binary-Error: One of the Expressions has type void");
            }
        } else {
            //This should never happen if the scanner works correctly
            throw new UnknownOperatorException(String.format("Binary-Error: The Operator %s is not supported", operator));
        }
    }

    @Override
    public void codeGen(ClassWriter cw, MethodVisitor method, Class i_class, Vector<LocalVarDecl> localVars) {
        // TODO Auto-generated method stub

    }
}
