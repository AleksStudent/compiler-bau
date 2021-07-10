import java.util.Map;
import java.util.Vector;

public class Block extends Stmt {

    public Vector<Stmt> statements;

    public Block(final Vector<Stmt> statements) {
        this.statements = statements;
    }

    @Override
    public String toString() {
        return "Block{" +
                "statements=" + this.statements +
                '}';
    }

    @Override
    public Type typeCheck(Map<String, Type> localVars, Class thisClass) {
        Type blockType = Type.TYPE_VOID;
        for (Stmt statement : statements) {
            Type statementType = statement.typeCheck(localVars, thisClass);
            if (!statementType.equals(Type.TYPE_VOID)){
                if(blockType.equals(Type.TYPE_VOID)||blockType.equals(statementType)){
                    blockType=statementType;
                }else{
                    throw new UnexpectedTypeException(String.format("Block-Error: Block with Type %s cannot have second Type %s of Statement %s",blockType,statementType,statement));
                }
            }
        }
        return blockType;
    }
}
