package kb.common.datastructure.useraction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import fr.uga.pddl4j.parser.Connective;
import fr.uga.pddl4j.parser.Exp;
import fr.uga.pddl4j.parser.NamedTypedList;
import fr.uga.pddl4j.parser.Symbol;
import fr.uga.pddl4j.parser.TypedSymbol;

/**
 * Created by Nattapon on 13/6/2560.
 */

public class UserActionParameter {

    String expressionValue;
    Double doubleValue;
    Exp valueExp;
    Exp valueForCompare;


    public String getExpValueParameter() {
        return expressionValue;
    }

    public void setExpValueParameter(String expressionValue) {
        this.expressionValue = expressionValue;
    }

    public Double getDoubleValue() {
        return doubleValue;
    }

    public void setDoubleValue(Double doubleValue) {
        this.doubleValue = doubleValue;
    }

    public Exp getValueExp() {
        return valueExp;
    }

    public void setValueExp(Exp valueExp) {
        this.valueExp = valueExp;
    }

    public Exp getValueForCompare() {
        return valueForCompare;
    }

    public void setValueForCompare(Exp valueForCompare) {
        this.valueForCompare = valueForCompare;
    }


    //The purpose of this is to transfrom the expression of drill size to be similar to the comparison in Action.
    public Exp transformValueToExp_withoutVariable( Double key_value, String key,
                                                        List<NamedTypedList> ftList,
                                                        Map<String, ArrayList<TypedSymbol>> typeMap  )
    {
        //1. Create instances
        //NoTe, just make it simple.
        //Exp exp = new Exp(Connective.FN_HEAD);
        Exp exp = new Exp(Connective.F_EXP);

        Exp child_exp = new Exp(Connective.FN_HEAD);

        //Children - Symbol 1
        Symbol s1 = new Symbol(Symbol.Kind.FUNCTOR, key );						//"size"
        //Symbol s2 = new Symbol(Kind.VARIABLE,  gts.getImage() );		// ground variable. e.g. ds

        //Children - Atom 2
        List<Symbol> sList = new ArrayList<>();
        sList.add(s1);
        //sList.add(s2);

        child_exp.setAtom(sList);
        exp.addChild( child_exp );
        exp.setValue( key_value );

        this.setValueExp(exp);

        return exp;
    }

    public Exp transformActionConstantToExpression(String predicate_action_name , String prediate_action_value )
    {
        Exp exp = new Exp(Connective.ATOM);

        Symbol predicateSymbol = new Symbol(Symbol.Kind.PREDICATE, predicate_action_name);
        //Symbol predicateValueSymbol = new Symbol(Kind.CONSTANT, prediate_action_value);		// in actionlist, Kind is variable.
        Symbol predicateValueSymbol = new Symbol(Symbol.Kind.VARIABLE, prediate_action_value);		// in actionlist, Kind is variable.

        List<Symbol> atoms = new ArrayList<>();
        atoms.add( predicateSymbol );
        atoms.add( predicateValueSymbol );

        exp.setAtom( atoms );

        return exp;
    }

    public Exp createPredicateUserAction(String predicate_action_name, boolean isNegation )
    {
        Exp exp = new Exp(Connective.ATOM);
        Symbol predicateSymbol = new Symbol(Symbol.Kind.PREDICATE, predicate_action_name);

        List<Symbol> atoms = new ArrayList<>();
        atoms.add( predicateSymbol );
        exp.setAtom( atoms );

        Exp finalExp = null;

        if( isNegation )
        {
            finalExp = new Exp(Connective.NOT);
            finalExp.addChild(exp);
        }
        else
        {
            finalExp = exp;
        }

        return finalExp;
    }

}
