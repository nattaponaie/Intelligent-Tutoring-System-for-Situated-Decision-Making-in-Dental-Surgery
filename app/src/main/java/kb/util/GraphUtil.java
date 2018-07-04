package kb.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.uga.pddl4j.parser.Connective;
import fr.uga.pddl4j.parser.Exp;
import fr.uga.pddl4j.parser.Symbol;
import kb.common.datastructure.graph.TutorActionConditionDetails;
import kb.common.datastructure.graph.TutorGraph;

import static com.surgical.decision3.common.controller.ApplicationController.currentAction;
import static com.surgical.decision3.common.controller.ApplicationController.tutorGraphList;

/**
 * Created by Nattapon on 14/7/2560.
 */

public class GraphUtil {

    private static List<Exp> mergeUserActionPredicate(List<Exp> clone_conditionExpList) {
        List<Exp> clone_conditionExpList2 = new ArrayList<>();

        for (TutorGraph each_tutor_graph : tutorGraphList)
        {
            if (each_tutor_graph.getActionNode().getActionNode().equals(currentAction))
            {
                for (Exp each_clone_condition : clone_conditionExpList)
                {
                    boolean found = false;

                    Symbol conditionSymbol = getExpSymbol(each_clone_condition);

                    if(conditionSymbol != null) {

                        for (TutorActionConditionDetails each_user_action : each_tutor_graph.getActionNode().getAcDetailsList()) {
                            Symbol userActionSymbol = getExpSymbol(each_user_action.getExp());

                            if(userActionSymbol != null)
                            {
                                if (conditionSymbol.equals(userActionSymbol)) {
                                    clone_conditionExpList2.add(each_user_action.getExp());
                                    found = true;
                                    break;
                                }
                            }
                        }

                        if (!found && !clone_conditionExpList2.contains(each_clone_condition)) {
                            clone_conditionExpList2.add(each_clone_condition);
                        }
                    }
                }
            }
        }

        return clone_conditionExpList2;
    }

    public static HashMap<String, String> getPerceptionAndComprehensionByComparingAC(Exp action_condition
            , HashMap<String, Exp> clone_PredicateExpDataMap
            , HashMap<String, String> clone_PerceptionWithComprehensionMap)
    {
        HashMap<String, String> PerceptionWithComprehensionMap = new HashMap<>();

        List<Exp> clone_conditionExpList = clonePredicateExp(action_condition);

        clone_conditionExpList = mergeUserActionPredicate(clone_conditionExpList);

        List<String> predicate_string = getPredicateStringInHashMap(clone_PredicateExpDataMap, clone_conditionExpList);

        List<String> remaining_predicate_string = new ArrayList<>();
        remaining_predicate_string.addAll(predicate_string);

        if(predicate_string.size() != 0) {

            for (Map.Entry<String, String> each_clone_map : clone_PerceptionWithComprehensionMap.entrySet()) {
                for (String each_predicate : predicate_string)
                {
                    if(each_clone_map.getKey().equals(each_predicate))
                    {
                        if(!PerceptionWithComprehensionMap.containsKey(each_clone_map.getKey()))
                        {
                            PerceptionWithComprehensionMap.put(each_clone_map.getKey(), each_clone_map.getValue());
                            remaining_predicate_string.remove(each_predicate);
                        }
                    }
                    else if(each_clone_map.getValue() != null)
                    {
                        if(each_clone_map.getValue().equals(each_predicate))
                        {
                            if(!PerceptionWithComprehensionMap.containsKey(each_clone_map.getKey()))
                            {
                                PerceptionWithComprehensionMap.put(each_clone_map.getKey(), each_clone_map.getValue());
                                remaining_predicate_string.remove(each_predicate);
                            }
                        }
                    }
                }
            }
        }

        for(String each_remaining_predicate_string : remaining_predicate_string)
        {
            if(!PerceptionWithComprehensionMap.containsKey(each_remaining_predicate_string))
            {
                PerceptionWithComprehensionMap.put(each_remaining_predicate_string, null);
            }
        }

        return PerceptionWithComprehensionMap;
    }

    public static List<Exp> clonePredicateExp(Exp predicate)
    {
        List<Exp> clone_exp = new ArrayList<>();

        if(predicate.getConnective().equals(Connective.LESS)
                || predicate.getConnective().equals(Connective.LESS_OR_EQUAL)
                || predicate.getConnective().equals(Connective.GREATER)
                || predicate.getConnective().equals(Connective.GREATER_OR_EQUAL)
                ||predicate.getConnective().equals(Connective.EQUAL)
                || predicate.getConnective().equals(Connective.FN_ATOM))
        {
            for(Exp child : predicate.getChildren())
            {
                if(child.getChildren().get(0).getAtom() != null) {
                    Exp node = new Exp(child.getConnective());
                    Symbol symbol = child.getChildren().get(0).getAtom().get(0);
                    List<Symbol> symbol_list = new ArrayList<Symbol>();
                    symbol_list.add(symbol);
                    node.setAtom(symbol_list);

                    clone_exp.add(node);
                }
            }

        }
        else if(predicate.getConnective().equals(Connective.ATOM) && predicate.getAtom().size() == 2 )
        {
            Exp node = new Exp(Connective.ATOM);
            Symbol symbol = predicate.getAtom().get(0);
            List<Symbol> symbol_list = new ArrayList<Symbol>();
            symbol_list.add(symbol);
            node.setAtom(symbol_list);

//                  System.out.println("toey "+ node);
            clone_exp.add(node);
        }
        else if(predicate.getConnective().equals(Connective.NOT))
        {
            Exp atom_node = new Exp(Connective.ATOM);
            Symbol symbol = predicate.getChildren().get(0).getAtom().get(0);
            List<Symbol> symbol_list = new ArrayList<Symbol>();
            symbol_list.add(symbol);
            atom_node.setAtom(symbol_list);

            Exp not_node = new Exp(Connective.NOT);
            not_node.addChild(atom_node);

//          System.out.println("toey "+ not_node);
            clone_exp.add(not_node);
        }
        else
        {
            clone_exp.add(predicate);
        }

        return clone_exp;
    }

    public static List<String> getPredicateStringInHashMap(HashMap<String, Exp> hashMap, List<Exp> expList)
    {
        List<String> predicate_string = new ArrayList<>();

        List<Exp> not_found_exp = new ArrayList<>();

        for (Map.Entry<String, Exp> hash_data : hashMap.entrySet()) {
            for(Exp each_exp : expList) {
                if(each_exp.getConnective().equals(Connective.NOT)) {
                    if(hash_data.getValue().equals(each_exp))
                    {
                        if (!predicate_string.contains(hash_data.getKey())) {
                            predicate_string.add(hash_data.getKey());
                        }
                    }
                    else
                    {
                        if(!not_found_exp.contains(each_exp)) not_found_exp.add(each_exp);
                    }
                }
                else
                {
                    Symbol each_exp_symbol = getExpSymbol(each_exp);
                    Symbol hash_data_symbol = getExpSymbol(hash_data.getValue());
                    if (each_exp_symbol.equals(hash_data_symbol)
                            && hash_data.getValue().getConnective().equals(each_exp.getConnective())) {
                        if (!predicate_string.contains(hash_data.getKey())) {
                            predicate_string.add(hash_data.getKey());
                        }
                    }
                    else
                    {
                        if(!not_found_exp.contains(each_exp)) not_found_exp.add(each_exp);
                    }
                }
            }
        }

        for(TutorGraph each_tutor_graph : tutorGraphList)
        {
            if (each_tutor_graph.getActionNode().getActionNode().equals(currentAction)) {

                for(Exp each_not_found_exp : not_found_exp)
                {
                    for (TutorActionConditionDetails tutorACDetails : each_tutor_graph.getActionNode().getAcDetailsList()) {

                        if (tutorACDetails.getExp().equals(each_not_found_exp))
                        {
                            if (!predicate_string.contains(each_not_found_exp.toString())) {
                                Symbol each_not_found_symbol = getExpSymbol(each_not_found_exp);
                                predicate_string.add(each_not_found_symbol.toString());
                                break;
                            }
                        }

                    }
                }
            }
        }

        return predicate_string;
    }

    public static Symbol getExpSymbol(Exp expValue)
    {
        Symbol symbol = null;

        if(expValue.getConnective().equals(Connective.F_EXP))
        {
            if(expValue.getChildren().size() != 0 && expValue.getChildren().get(0).getAtom() != null)
            {
                symbol = expValue.getChildren().get(0).getAtom().get(0);
            }
            else if(expValue.getAtom() != null)
            {
                symbol = expValue.getAtom().get(0);
            }
        }
        else if(expValue.getConnective().equals(Connective.FN_HEAD))
        {
            symbol = expValue.getAtom().get(0);
        }
        else if(expValue.getConnective().equals(Connective.NOT) && expValue.getChildren().size() != 0)
        {
            symbol = expValue.getChildren().get(0).getAtom().get(0);
        }
        else if( expValue.getAtom() != null )
        {
            symbol = expValue.getAtom().get(0);
        }

        return symbol;
    }
}
