package kb.util;

import com.surgical.decision3.common.controller.ApplicationController;
import com.surgical.decision3.functions.TransformStudentAction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.uga.pddl4j.parser.Connective;
import fr.uga.pddl4j.parser.Exp;
import fr.uga.pddl4j.parser.Symbol;
import kb.common.constants.GraphNodeConstants;
import kb.common.datastructure.graph.StudentActionConditionDetails;
import kb.common.datastructure.graph.StudentGraph;
import kb.common.datastructure.graph.StudentGraphNode;
import kb.common.datastructure.graph.TutorActionConditionDetails;
import kb.common.datastructure.graph.TutorGraph;
import kb.common.datastructure.oneofprojection.OneOfGroup;
import kb.common.datastructure.oneofprojection.OneOfNode;
import kb.common.datastructure.solutionpath.PlanAction;
import kb.common.datastructure.useraction.ActionDetails;
import kb.common.datastructure.useraction.UserActionDetails;
import kb.common.datastructure.worldstate.State;

import static com.surgical.decision3.common.controller.ApplicationController.currentAction;
import static com.surgical.decision3.common.controller.ApplicationController.studentGraphList;
import static com.surgical.decision3.common.controller.ApplicationController.tutorGraphList;
import static com.surgical.decision3.functions.TransformStudentAction.studentGraphObject;
import static kb.util.GraphUtil.getExpSymbol;
import static kb.util.GraphUtil.getPerceptionAndComprehensionByComparingAC;
import static kb.util.StringUtil.subStringExp;
import static kb.util.TutorGraphUtil.getPerceptionSubStringExp;
import static kb.util.TutorGraphUtil.isThisPredicateUserAction;

/**
 * Created by Nattapon on 6/7/2560.
 */

public class StudentGraphUtil {

    public static void initializeStudentGraph() {
        addStateNode();
    }

    public static void addStudentStateGraph()
    {
        if(studentGraphObject.getStateNode().getState() == null) StudentGraphUtil.addStateNode();
    }

    public static void addStateNode()
    {
        StudentGraphNode state_node = new StudentGraphNode(GraphNodeConstants.NODE_TYPE_STATE);

        state_node = setAllPerceptionAndComprehension(state_node, WorldStateUtil.getCurrentFactPredicate());

        Integer state_id = WorldStateUtil.getCurrentStateID();
        State clone_state = new State(state_id);
        clone_state.setS_id(state_id);
        clone_state.setFactPredicate(WorldStateUtil.getCurrentFactPredicate());
        clone_state.setFactAndEffect(WorldStateUtil.getCurrentFactAndEffect());
        clone_state.setFactAndActionPredicate(WorldStateUtil.getCurrentFactAndActionPredicate());
        clone_state.setTrialHistory(WorldStateUtil.getCurrentTrailHistory());

        state_node.setState(clone_state);

        studentGraphObject.setStateNode(state_node);

        addPerceptionAndComprehensionNode();
    }

    public static void addPerceptionAndComprehensionNode()
    {
        HashMap<String,String> perceptionAndComprehensionStringMap
                = studentGraphObject.getStateNode().getPerceptionWithComprehensionHashMap();

        studentGraphObject.getPerceptionList().clear();
        studentGraphObject.getComprehensionList().clear();
        studentGraphObject.getStateNode().getChildNodes().clear();

        for (Map.Entry<String, String> each_perception_comprehension : perceptionAndComprehensionStringMap.entrySet()) {

            Exp perceptionExp = getPredicateExpFromHashMap(each_perception_comprehension.getKey());
            Exp comprehensionExp = null;
            if(each_perception_comprehension.getValue() != null)
            {
                comprehensionExp = getPredicateExpFromHashMap(each_perception_comprehension.getValue());
            }

            if(perceptionExp != null)
            {
                String perceptionString = getPerceptionSubStringExp(perceptionExp);
                Pattern pattern = Pattern.compile("\\s");
                Matcher matcher = pattern.matcher(perceptionString);
                boolean hasConstantValue = matcher.find();
                if(hasConstantValue)
                {
                    String[] basePredicateString = perceptionString.split(" ");
                    perceptionString = basePredicateString[0];
                }

                if(!isThisPredicateUserAction(perceptionString)) {
                    StudentGraphNode perceptionNode = new StudentGraphNode(GraphNodeConstants.NODE_TYPE_PERCEPTION);
                    perceptionNode.setPerceptionExp(perceptionExp);
                    perceptionNode.addParentNode(studentGraphObject.getStateNode());

                    studentGraphObject.getStateNode().addChildNode(perceptionNode);

                    StudentGraphNode comprehensionNode = null;
                    if(comprehensionExp != null)
                    {
                        comprehensionNode = new StudentGraphNode(GraphNodeConstants.NODE_TYPE_COMPREHENSION);
                        comprehensionNode.setComprehensionExp(comprehensionExp);
                        comprehensionNode.addParentNode(perceptionNode);

                        if(studentGraphObject.getActionNode().getActionNode() != null)
                        {
                            comprehensionNode.addChildNode(studentGraphObject.getActionNode());
                        }

                        perceptionNode.addChildNode(comprehensionNode);
                    }
                    else
                    {
                        if(studentGraphObject.getActionNode().getActionNode() != null)
                        {
                            perceptionNode.addChildNode(studentGraphObject.getActionNode());
                        }
                    }

                    studentGraphObject.getPerceptionList().add(perceptionNode);
                    if(comprehensionNode != null) studentGraphObject.getComprehensionList().add(comprehensionNode);
                }
            }
        }
    }

    private static Exp getPredicateExpFromHashMap(String predicateString)
    {
        HashMap<String,Exp> PredicateExpMap
                = studentGraphObject.getStateNode().getPredicateExpDataHashMap();

        for (Map.Entry<String, Exp> each_predicate_exp : PredicateExpMap.entrySet()) {
            if(each_predicate_exp.getKey().equals(predicateString))
            {
                return each_predicate_exp.getValue();
            }
        }

        return null;
    }

    public static void updatePerceptionAndComprehension()
    {
        StudentGraphNode state_node = new StudentGraphNode(GraphNodeConstants.NODE_TYPE_STATE);
        state_node = setAllPerceptionAndComprehension(state_node, WorldStateUtil.getCurrentFactAndActionPredicate());

        state_node.setState(studentGraphObject.getStateNode().getState());

        studentGraphObject.setStateNode(state_node);
    }

    public static void updateStudentStateNode(StudentGraph studentGraphObject)
    {
        StudentGraphNode clone_node;
        clone_node = setAllPerceptionAndComprehension(studentGraphObject.getStateNode(), WorldStateUtil.getCurrentFactAndActionPredicate());

        studentGraphObject.getStateNode().getPerceptionWithComprehensionHashMap().putAll(clone_node.getPerceptionWithComprehensionHashMap());
        studentGraphObject.getStateNode().getPredicateExpDataHashMap().putAll(clone_node.getPredicateExpDataHashMap());
    }

    public static StudentGraphNode setAllPerceptionAndComprehension(StudentGraphNode graphNode, List<Exp> currentFacts)
    {
        HashMap<String, String> perceptionToComprehensionMap = new HashMap<>();
        HashMap<String, Exp> predicateExpData = new HashMap<>();

        for (Exp each_fact_predicate : currentFacts) {

//            System.out.println("toey2 " +each_fact_predicate);

            Exp perception_exp = InferenceEngineUtil.getStudentPerceptionFromAxiom(each_fact_predicate);

            if (perception_exp != null) {

                Exp comprehension_exp = InferenceEngineUtil.getComprehensionFromAxiom(perception_exp);
//                System.out.println("toey perception "+ perception_exp +" comprehension "+comprehension_exp);

                String comprehension_string = subStringExp(comprehension_exp);

                ArrayList<String> perception_string_list = StringUtil.getSubStringFromExpList(perception_exp);

                for(String each_predicate_string : perception_string_list)
                {
                    perceptionToComprehensionMap.put(each_predicate_string,comprehension_string);

                    if(each_fact_predicate.getConnective().equals(Connective.ATOM) && each_fact_predicate.getAtom().size() == 2 )
                    {
                        Exp node = new Exp(Connective.ATOM);
                        Symbol symbol = each_fact_predicate.getAtom().get(0);
                        List<Symbol> symbol_list = new ArrayList<Symbol>();
                        symbol_list.add(symbol);
                        node.setAtom(symbol_list);

//                  System.out.println("toey "+ node);
                        if(!predicateExpData.containsKey(each_predicate_string)) predicateExpData.put(each_predicate_string, node);
                    }
                    else if(!predicateExpData.containsKey(each_predicate_string)) predicateExpData.put(each_predicate_string, perception_exp);
//                    System.out.println("toey each_predicate_string "+ each_predicate_string +" comprehension_string "+comprehension_string);
                }
                predicateExpData.put(comprehension_string, comprehension_exp);
            }
            else
            {
                graphNode = putAllPredicateExpData(graphNode, each_fact_predicate);
            }
        }

        graphNode.getPerceptionWithComprehensionHashMap().putAll(perceptionToComprehensionMap);
        graphNode.getPredicateExpDataHashMap().putAll(predicateExpData);

        return graphNode;
    }

    public static void addActionNode(PlanAction Action) {

        StudentGraphNode action_node = new StudentGraphNode(GraphNodeConstants.NODE_TYPE_ACTION);
        action_node.setActionNode(Action);

        for (StudentGraphNode each_perception : studentGraphObject.getPerceptionList()) {
            if(!doesThisNodeContainComprehensionNodeInChild(each_perception))
            {
                action_node.addParentNode(each_perception);
                each_perception.addChildNode(action_node);
            }
        }
        for (StudentGraphNode each_comprehension : studentGraphObject.getComprehensionList()) {
            action_node.addParentNode(each_comprehension);
            each_comprehension.addChildNode(action_node);
        }

        studentGraphObject.setActionNode(action_node);

        addUserActionDetailsToActionNode();
    }

    private static boolean doesThisNodeContainComprehensionNodeInChild(StudentGraphNode perceptionNode)
    {
        boolean contain = false;

        for(StudentGraphNode each_child_node : perceptionNode.getChildNodes())
        {
            if(each_child_node.getNodeType().equals(GraphNodeConstants.NODE_TYPE_COMPREHENSION))
            {
                contain = true;
                break;
            }
        }

        return contain;
    }

    private static void addUserActionDetailsToActionNode()
    {
        boolean foundAction = false;
        for(TutorGraph each_tutor_graph : tutorGraphList) {
            if(each_tutor_graph.getActionNode().getActionNode().equals(currentAction))
            {
                foundAction = true;
                for (ActionDetails ad : UserActionDetails.actionWithPredicate) {

                    for (Map.Entry<String, Exp> predicateExpData : each_tutor_graph.getPerceptionAndComprehensionNode()
                            .getPredicateExpDataHashMap().entrySet())
                    {
                        if(predicateExpData.getKey().equals(ad.getPredicate()))
                        {
                            StudentActionConditionDetails acDetail = new StudentActionConditionDetails();
                            acDetail.setExp(predicateExpData.getValue());
                            acDetail.setType("UserActionDetails");
                            acDetail.setRefGraphNode(studentGraphObject.getActionNode());

                            studentGraphObject.getActionNode().getAcDetailsList().add(acDetail);

                            break;
                        }
                    }

                }
            }
            if(foundAction) break;
        }
    }

    public static void addActionConditionNode(Exp conditionExp)
    {
        StudentGraphNode graph_action_condition_node
                = new StudentGraphNode(GraphNodeConstants.NODE_TYPE_ACTION_CONDITION);

        graph_action_condition_node.setActionCondition(conditionExp);
        graph_action_condition_node.addParentNode(studentGraphObject.getActionNode());

        //-----------------Add Perception & Comprehension to HashMap -----------------//
        HashMap<String, String> perceptionWithComprehensionMap = new HashMap<>();

        perceptionWithComprehensionMap.putAll(
                getPerceptionWithComprehensionMap(conditionExp,
                        studentGraphObject));

        graph_action_condition_node.setPerceptionWithComprehensionList(
                getPerceptionWithComprehensionListFromMap(perceptionWithComprehensionMap, studentGraphObject));

        graph_action_condition_node.setPerceptionWithComprehensionHashMap(perceptionWithComprehensionMap);

        //-------------------add Action Condition Details------------------------------//
        graph_action_condition_node = addActionConditionDetailInACNode(studentGraphObject, graph_action_condition_node);

        //-------------------------------------------------------------------//

        studentGraphObject.getActionNode().addChildNode(graph_action_condition_node);

        studentGraphObject.getActionConditionList().add(graph_action_condition_node);
    }

    public static void addProjectionNode(Exp conditionExp, Exp effectExp)
    {

        for(StudentGraphNode eachActionConNode : studentGraphObject.getActionConditionList())
        {
            //if this node is action node
            if (eachActionConNode.getNodeType().equals(GraphNodeConstants.NODE_TYPE_ACTION_CONDITION))
            {
                if(eachActionConNode.getActionCondition().equals(conditionExp)) {

                    boolean foundProjectionNode = false;
                    for(StudentGraphNode each_projection_node : studentGraphObject.getProjectionList())
                    {
                        if(each_projection_node.getProjectionExp().equals(effectExp))
                        {
                            each_projection_node.addParentNode(eachActionConNode);
                            eachActionConNode.addChildNode(each_projection_node);
                            foundProjectionNode = true;
                            break;
                        }
                    }
                    if(!foundProjectionNode)
                    {
                        StudentGraphNode graph_projection_node = new StudentGraphNode(GraphNodeConstants.NODE_TYPE_PROJECTION);
                        graph_projection_node.setProjectionExp(effectExp);
                        graph_projection_node.addParentNode(eachActionConNode);


                        eachActionConNode.addChildNode(graph_projection_node);
                        studentGraphObject.getProjectionList().add(graph_projection_node);
                    }
                }
            }
        }
    }

//    public static void addProjectionNode(Exp conditionExp, List<Exp> effectExpList)
//    {
//
//        for(StudentGraphNode eachActionConNode : studentGraphObject.getActionConditionList())
//        {
//            //if this node is action node
//            if (eachActionConNode.getNodeType().equals(GraphNodeConstants.NODE_TYPE_ACTION_CONDITION))
//            {
//                if(eachActionConNode.getActionCondition().equals(conditionExp)) {
//
//                    for(Exp each_effectExpList : effectExpList)
//                    {
//
//                        boolean foundProjectionNode = false;
//                        for(StudentGraphNode each_projection_node : studentGraphObject.getProjectionList())
//                        {
//                            if(each_projection_node.getProjectionExp().equals(each_effectExpList))
//                            {
//                                each_projection_node.addParentNode(eachActionConNode);
//                                eachActionConNode.addChildNode(each_projection_node);
//                                foundProjectionNode = true;
//                                break;
//                            }
//                        }
//                        if(!foundProjectionNode)
//                        {
//                            StudentGraphNode graph_projection_node = new StudentGraphNode(GraphNodeConstants.NODE_TYPE_PROJECTION);
//                            graph_projection_node.setProjectionExp(each_effectExpList);
//                            graph_projection_node.addParentNode(eachActionConNode);
//
//
//                            eachActionConNode.addChildNode(graph_projection_node);
//                            studentGraphObject.getProjectionList().add(graph_projection_node);
//                        }
//                    }
//                }
//            }
//        }
//    }

    /**
     * Mes - class for adding ONEOF effect to student graph - CODE IS NOT REFACTORED
     * @param conditionExp
     * @param oneOfNodeList
     */
    public static void addOneOfProjectionNode(Exp conditionExp, List<OneOfNode> oneOfNodeList)
    {

        //attributes to handle ONEOF algorithm
        List<Exp> effectExpList = new ArrayList<>();
        Exp oneOfOccurredEffectExp = null;
        OneOfGroup oneOfGroup = new OneOfGroup();

        //get all possible effect in ONEOF list
        for(OneOfNode oneOfNode : oneOfNodeList){
            oneOfGroup.addOneOfNode(oneOfNode);
            effectExpList.add(oneOfNode.getExp());
            if(oneOfNode.isOneOfOccur()){
                oneOfOccurredEffectExp = oneOfNode.getExp();
            }
        }



        for(StudentGraphNode eachActionConNode : studentGraphObject.getActionConditionList())
        {
            //if this node is action node
            if (eachActionConNode.getNodeType().equals(GraphNodeConstants.NODE_TYPE_ACTION_CONDITION))
            {
                if(eachActionConNode.getActionCondition().equals(conditionExp)) {

                    for(Exp each_effectExpList : effectExpList)
                    {

                        boolean foundProjectionNode = false;
                        for(StudentGraphNode each_projection_node : studentGraphObject.getProjectionList())
                        {
                            if(each_projection_node.getProjectionExp().equals(each_effectExpList))
                            {
                                if(each_projection_node.getProjectionExp().equals(oneOfOccurredEffectExp)){
                                    each_projection_node.setOneOfOccur(true);
                                }

                                each_projection_node.setOneOf(true);
                                each_projection_node.setOneOfGroup(oneOfGroup);
                                oneOfGroup.addOneOfStudentGraphNode(each_projection_node);
                                for(OneOfNode oneOfNode : oneOfNodeList){
                                    if(oneOfNode.getExp().equals(each_effectExpList)){
                                        each_projection_node.setOneOfProbability(oneOfNode.getProbabilityValue());
                                        break;
                                    }
                                }
                                eachActionConNode.addChildNode(each_projection_node);

                                foundProjectionNode = true;
                                break;
                            }
                        }
                        if(!foundProjectionNode)
                        {
                            StudentGraphNode graph_projection_node = new StudentGraphNode(GraphNodeConstants.NODE_TYPE_PROJECTION);
                            graph_projection_node.setProjectionExp(each_effectExpList);
                            graph_projection_node.addParentNode(eachActionConNode);

                            if(graph_projection_node.getProjectionExp().equals(oneOfOccurredEffectExp)){
                                graph_projection_node.setOneOfOccur(true);
                            }

                            graph_projection_node.setOneOf(true);
                            graph_projection_node.setOneOfGroup(oneOfGroup);
                            oneOfGroup.addOneOfStudentGraphNode(graph_projection_node);
                            for(OneOfNode oneOfNode : oneOfNodeList){
                                if(oneOfNode.getExp().equals(each_effectExpList)){
                                    graph_projection_node.setOneOfProbability(oneOfNode.getProbabilityValue());
                                    break;
                                }
                            }

                            eachActionConNode.addChildNode(graph_projection_node);
                            studentGraphObject.getProjectionList().add(graph_projection_node);
                        }
                    }
                }
            }
        }
    }

    public static void updateProjectionNode(Exp desired_outcome)
    {
        for(StudentGraphNode eachProjectionNode : TransformStudentAction.studentGraphObject.getProjectionList())//read all student graph
        {
            for(StudentGraphNode this_parent_node : eachProjectionNode.getParentNodes())//check its parents
            {
                //if its parent is Action condition node
                if(this_parent_node.getNodeType().equals(GraphNodeConstants.NODE_TYPE_ACTION_CONDITION))
                {
                    //if the action name is equal then
                    //check is their desired outcome equal or not
                    if(eachProjectionNode.getProjectionExp().equals(desired_outcome))
                    {
                        //if equal update the desired outcome to true in this projection node
                        eachProjectionNode.setDesiredOutCome(true);
                        if(desired_outcome.isMain()){
                            eachProjectionNode.setMain(true);
                        }
                    }
                }
            }
        }
    }

    public static void addEndNode(boolean isSatisfyDO, boolean isInvalidAction
            , StudentGraph studentGraphObject)
    {

        StudentGraphNode end_node = new StudentGraphNode(GraphNodeConstants.NODE_TYPE_END);

        if(!isInvalidAction)
        {
            boolean isEndNodeAdded = false;
            for(StudentGraphNode eachProjectionNode : studentGraphObject.getProjectionList())
            {
                end_node.addParentNode(eachProjectionNode);
                end_node.setSatisfyDesiredOutcome(isSatisfyDO);
                end_node.setInvalidAction(isInvalidAction);

                eachProjectionNode.addChildNode(end_node);

                studentGraphObject.setEndNode(end_node);
                isEndNodeAdded = true;
            }
            if(!isEndNodeAdded)
            {
                end_node.addParentNode(studentGraphObject.getActionNode());
                end_node.setSatisfyDesiredOutcome(isSatisfyDO);
                end_node.setInvalidAction(isInvalidAction);

                studentGraphObject.getActionNode().addChildNode(end_node);

                studentGraphObject.setEndNode(end_node);
            }

        }
        else if(isInvalidAction)
        {
            end_node.addParentNode(studentGraphObject.getActionNode());
            end_node.setSatisfyDesiredOutcome(isSatisfyDO);
            end_node.setInvalidAction(isInvalidAction);

            studentGraphObject.getActionNode().addChildNode(end_node);

            studentGraphObject.setEndNode(end_node);
        }
    }

    public static void processStudentNode(boolean is_outcome_expected, boolean isInvalidAction)
    {
        //add end node
        StudentGraphUtil.addEndNode(is_outcome_expected, isInvalidAction, studentGraphObject);

        StudentGraph clone_student_graph = getCloneStudentGraph();

        ApplicationController.studentGraphList.add(clone_student_graph);


        if(is_outcome_expected)
        {
            clearAllGraphValue();
        }
        else
        {
            clearGraphValue();
        }

    }

    private static StudentGraph getCloneStudentGraph()
    {
        StudentGraph clone_student_graph = new StudentGraph();
        clone_student_graph.setStateNode(studentGraphObject.getStateNode());
        clone_student_graph.getPerceptionList().addAll(studentGraphObject.getPerceptionList());
        clone_student_graph.getComprehensionList().addAll(studentGraphObject.getComprehensionList());
        clone_student_graph.setActionNode(studentGraphObject.getActionNode());
        clone_student_graph.getActionConditionList().addAll(studentGraphObject.getActionConditionList());
        clone_student_graph.getProjectionList().addAll(studentGraphObject.getProjectionList());
        clone_student_graph.setEndNode(studentGraphObject.getEndNode());

        return clone_student_graph;
    }

    private static void clearAllGraphValue()
    {
        StudentGraphNode new_state_node = new StudentGraphNode(GraphNodeConstants.NODE_TYPE_STATE);
        StudentGraphNode new_end_node = new StudentGraphNode(GraphNodeConstants.NODE_TYPE_END);
        StudentGraphNode new_action_node = new StudentGraphNode(GraphNodeConstants.NODE_TYPE_ACTION);
        studentGraphObject.setStateNode(new_state_node);
        studentGraphObject.setEndNode(new_end_node);
        studentGraphObject.setActionNode(new_action_node);
        studentGraphObject.getActionConditionList().clear();
        studentGraphObject.getPerceptionList().clear();
        studentGraphObject.getComprehensionList().clear();
        studentGraphObject.getProjectionList().clear();
    }

    private static void clearGraphValue()
    {
        StudentGraphNode new_end_node = new StudentGraphNode(GraphNodeConstants.NODE_TYPE_END);
        StudentGraphNode new_action_node = new StudentGraphNode(GraphNodeConstants.NODE_TYPE_ACTION);
        studentGraphObject.setEndNode(new_end_node);
        studentGraphObject.setActionNode(new_action_node);
        studentGraphObject.getActionConditionList().clear();
        studentGraphObject.getProjectionList().clear();
    }

    public static HashMap<String, String> getPerceptionWithComprehensionMap(Exp conditionExp
            , StudentGraph studentGraphObject)
    {
        HashMap<String, String> PerceptionWithComprehensionMap = new HashMap<>();
        HashMap<String, String> clone_PerceptionWithComprehensionMap = new HashMap<>();
        HashMap<String, Exp> clone_PredicateExpDataMap = new HashMap<>();

        clone_PredicateExpDataMap.putAll(getPredicateExpDataFromGraphNode(studentGraphObject));
        clone_PerceptionWithComprehensionMap.putAll(getPercAndCompFromGraphNode(studentGraphObject));

        if(conditionExp.getChildren().size() != 0 && !conditionExp.getConnective().equals(Connective.NOT))
        {
            for (Exp each_condition : conditionExp.getChildren()) {

                PerceptionWithComprehensionMap.putAll(getPerceptionAndComprehensionByComparingAC(each_condition
                        ,clone_PredicateExpDataMap, clone_PerceptionWithComprehensionMap));
            }
        }
        else
        {
            PerceptionWithComprehensionMap.putAll(getPerceptionAndComprehensionByComparingAC(conditionExp
                    ,clone_PredicateExpDataMap, clone_PerceptionWithComprehensionMap));
        }

        return PerceptionWithComprehensionMap;
    }

    private static StudentGraphNode putAllPredicateExpData(StudentGraphNode graphNode, Exp each_predicate)
    {
        HashMap<String, String> perceptionToComprehensionMap = new HashMap<>();
        HashMap<String, Exp> predicateExpData = new HashMap<>();

        if(StringUtil.getSubStringFromExpList(each_predicate).size() != 0)
        {
            for(String each_predicate_string : StringUtil.getSubStringFromExpList(each_predicate)) {
                perceptionToComprehensionMap.put(each_predicate_string, null);

//                if(each_predicate.getConnective().equals(Connective.EQUAL)
//                        || each_predicate.getConnective().equals(Connective.FN_ATOM))
//                {
////                    System.out.println("toey " + each_predicate.getChildren().get(0));
//                    predicateExpData.put(each_predicate_string, each_predicate.getChildren().get(0));
//                }
//                else if(each_predicate.getConnective().equals(Connective.LESS)
//                        || each_predicate.getConnective().equals(Connective.LESS_OR_EQUAL)
//                        || each_predicate.getConnective().equals(Connective.GREATER)
//                        || each_predicate.getConnective().equals(Connective.GREATER_OR_EQUAL))
//                {
//                    for(Exp child : each_predicate.getChildren())
//                    {
//                        String func_string = child.toString()
//                                .substring(1, child.toString().length()-1);
//
//                        if(each_predicate_string.equals(func_string)) predicateExpData.put(each_predicate_string, child);
//                    }
//
//                }
//                else if(each_predicate.getConnective().equals(Connective.ATOM) && each_predicate.getAtom().size() == 2 )
//                {
//                    Exp node = new Exp(Connective.ATOM);
//                    Symbol symbol = each_predicate.getAtom().get(0);
//                    List<Symbol> symbol_list = new ArrayList<Symbol>();
//                    symbol_list.add(symbol);
//                    node.setAtom(symbol_list);
//
////                  System.out.println("toey "+ node);
//                    predicateExpData.put(each_predicate_string, node);
//                }
                if(each_predicate.getConnective().equals(Connective.NOT))
                {
                    if(each_predicate.getChildren().size() != 0)
                    {
                        Exp atom_node = new Exp(Connective.ATOM);
                        Symbol symbol = each_predicate.getChildren().get(0).getAtom().get(0);
                        List<Symbol> symbol_list = new ArrayList<Symbol>();
                        symbol_list.add(symbol);
                        atom_node.setAtom(symbol_list);

                        Exp not_node = new Exp(Connective.NOT);
                        not_node.addChild(atom_node);

//                        System.out.println("toey "+ not_node);
                        predicateExpData.put(each_predicate_string, not_node);
                    }
                }
                else
                {
                    predicateExpData.put(each_predicate_string, each_predicate);
                }
            }
        }

        graphNode.getPerceptionWithComprehensionHashMap().putAll(perceptionToComprehensionMap);
        graphNode.getPredicateExpDataHashMap().putAll(predicateExpData);

        return graphNode;
    }

    private static List<TutorActionConditionDetails> getTutorACDetails()
    {
        List<TutorActionConditionDetails> acDetailsList = new ArrayList<>();

        for(TutorGraph each_tutor_graph : tutorGraphList)
        {
            if(each_tutor_graph.getActionNode().getActionNode().equals(currentAction))
            {
                acDetailsList.addAll(each_tutor_graph.getActionNode().getAcDetailsList());
                break;
            }
        }

        return acDetailsList;
    }

    private static StudentGraphNode addActionConditionDetailInACNode(StudentGraph studentGraphObject, StudentGraphNode graph_action_condition_node)
    {
        List<StudentGraphNode[]> clonePerceptionWithComprehensionList = new ArrayList<>();
        clonePerceptionWithComprehensionList.addAll(graph_action_condition_node.
                getPerceptionWithComprehensionList());

        //firstly, add User Action predicate
        for (StudentGraphNode[] percepWithCompString : graph_action_condition_node.
                getPerceptionWithComprehensionList())
        {
            for (TutorActionConditionDetails acDetails : getTutorACDetails())
            {
                if(percepWithCompString[0].getPerceptionExp().getConnective().equals(Connective.NOT))
                {
                    Symbol acDetailSymbol = getExpSymbol(acDetails.getExp());
                    if(acDetailSymbol.equals(
                            percepWithCompString[0].getPerceptionExp().getChildren().get(0).getAtom().get(0)))
                    {
                        StudentActionConditionDetails studentActionDetail = new StudentActionConditionDetails();
                        studentActionDetail.setExp(acDetails.getExp());
                        studentActionDetail.setType(acDetails.getType());

                        StudentGraphNode clone_actionNode = new StudentGraphNode(GraphNodeConstants.NODE_TYPE_ACTION);
                        clone_actionNode.setActionNode(acDetails.getRefGraphNode().getActionNode());

                        studentActionDetail.setRefGraphNode(clone_actionNode);

                        clonePerceptionWithComprehensionList.remove(percepWithCompString);

                        graph_action_condition_node.getAcDetailsList().add(studentActionDetail);
                    }
                }
                else if(acDetails.getExp().equals(percepWithCompString[0].getPerceptionExp()))
                {
                    StudentActionConditionDetails studentActionDetail = new StudentActionConditionDetails();
                    studentActionDetail.setExp(acDetails.getExp());
                    studentActionDetail.setType(acDetails.getType());

                    StudentGraphNode clone_actionNode = new StudentGraphNode(GraphNodeConstants.NODE_TYPE_ACTION);
                    clone_actionNode.setActionNode(acDetails.getRefGraphNode().getActionNode());

                    studentActionDetail.setRefGraphNode(clone_actionNode);

                    clonePerceptionWithComprehensionList.remove(percepWithCompString);

                    graph_action_condition_node.getAcDetailsList().add(studentActionDetail);
                }
            }
        }

        //secondly, add perception and comprehension predicate
        for (StudentGraphNode[] percepWithCompString : clonePerceptionWithComprehensionList)
        {
            StudentActionConditionDetails studentPerceptionDetail = new StudentActionConditionDetails();
            StudentGraphNode perceptionNode = getPerceptionNode(studentGraphObject, percepWithCompString[0].getPerceptionExp());
            if(perceptionNode != null)
            {
                studentPerceptionDetail.setExp(perceptionNode.getPerceptionExp());
                studentPerceptionDetail.setType(perceptionNode.getNodeType());
                studentPerceptionDetail.setRefGraphNode(perceptionNode);

                graph_action_condition_node.getAcDetailsList().add(studentPerceptionDetail);
            }


            if(percepWithCompString[1].getComprehensionExp() != null)
            {
                StudentActionConditionDetails studentComprehensionDetail = new StudentActionConditionDetails();
                StudentGraphNode compNode = getComprehensionNode(studentGraphObject, percepWithCompString[1].getComprehensionExp());
                if(compNode != null)
                {
                    studentComprehensionDetail.setExp(compNode.getComprehensionExp());
                    studentComprehensionDetail.setType(compNode.getNodeType());
                    studentComprehensionDetail.setRefGraphNode(compNode);

                    graph_action_condition_node.getAcDetailsList().add(studentComprehensionDetail);
                }
            }
        }

        return graph_action_condition_node;
    }

    private static StudentGraphNode getPerceptionNode(StudentGraph studentGraphObject,Exp perceptionExp)
    {
        for(StudentGraphNode each_perception_node : studentGraphObject.getPerceptionList())
        {
            if(each_perception_node.getPerceptionExp().equals(perceptionExp))
            {
                return each_perception_node;
            }
        }

        return null;
    }

    private static StudentGraphNode getComprehensionNode(StudentGraph studentGraphObject,Exp comprehensionExp)
    {
        for(StudentGraphNode each_comp_node : studentGraphObject.getComprehensionList())
        {
            if(each_comp_node.getComprehensionExp().equals(comprehensionExp))
            {
                return each_comp_node;
            }
        }

        return null;
    }

    public static HashMap<String, String> getPerceptionWithComprehensionFromAC(Exp conditionExp
            , List<StudentGraphNode> graphNodes, String nodeConstants)
    {
        HashMap<String, String> PerceptionWithComprehensionMap = new HashMap<>();
        HashMap<String, String> clone_PerceptionWithComprehensionMap = new HashMap<>();
        HashMap<String, Exp> clone_PredicateExpDataMap = new HashMap<>();

        clone_PredicateExpDataMap.putAll(getPredicateExpDataFromGraphNode(studentGraphObject));
        clone_PerceptionWithComprehensionMap.putAll(getPercAndCompFromGraphNode(studentGraphObject));

        if(conditionExp.getChildren().size() != 0 && !conditionExp.getConnective().equals(Connective.NOT))
        {
            for (Exp each_condition : conditionExp.getChildren()) {

                PerceptionWithComprehensionMap.putAll(getPerceptionAndComprehensionByComparingAC(each_condition
                        ,clone_PredicateExpDataMap, clone_PerceptionWithComprehensionMap));
            }
        }
        else
        {
            PerceptionWithComprehensionMap.putAll(getPerceptionAndComprehensionByComparingAC(conditionExp
                    ,clone_PredicateExpDataMap, clone_PerceptionWithComprehensionMap));
        }

        return PerceptionWithComprehensionMap;
    }

    public static HashMap<String, Exp> getPredicateExpDataFromGraphNode(StudentGraph studentGraphObject)
    {
        HashMap<String, Exp> clone_PredicateExpDataMap = new HashMap<>();


        clone_PredicateExpDataMap.putAll(studentGraphObject.getStateNode().getPredicateExpDataHashMap());


        return clone_PredicateExpDataMap;
    }

    public static HashMap<String, String> getPercAndCompFromGraphNode(StudentGraph studentGraphObject)
    {
        HashMap<String, String> clone_PercAndCompMap = new HashMap<>();

        clone_PercAndCompMap.putAll(studentGraphObject.getStateNode().getPerceptionWithComprehensionHashMap());


        return clone_PercAndCompMap;
    }

    public static List<StudentGraphNode[]> getPerceptionWithComprehensionListFromMap(HashMap<String, String> perceptionWithComprehensionMap
            , StudentGraph studentGraphObject)
    {
        List<StudentGraphNode[]> perceptionWithComprehensionList = new ArrayList<>();

        HashMap<String, String> remainingPerceptionWithComprehensionMap = new HashMap<>();
        remainingPerceptionWithComprehensionMap.putAll(perceptionWithComprehensionMap);

        for (Map.Entry<String, Exp> predicateExp : studentGraphObject.getStateNode().getPredicateExpDataHashMap().entrySet())
        {
            for (Map.Entry<String, String> percepWithCompString : perceptionWithComprehensionMap.entrySet())
            {
                StudentGraphNode[] studentGraphNodesArray = new StudentGraphNode[2];

                StudentGraphNode perceptionNode = new StudentGraphNode(GraphNodeConstants.NODE_TYPE_PERCEPTION);
                StudentGraphNode comprehensionNode = new StudentGraphNode(GraphNodeConstants.NODE_TYPE_COMPREHENSION);

                if(percepWithCompString.getKey().equals(predicateExp.getKey()))
                {
                    perceptionNode.setPerceptionExp(predicateExp.getValue());
                    if(percepWithCompString.getValue() != null) {
                        comprehensionNode.setComprehensionExp(getComprehensionExpFromHashMap(percepWithCompString.getValue(), studentGraphObject));
                    }

                    studentGraphNodesArray[0] = perceptionNode;
                    studentGraphNodesArray[1] = comprehensionNode;

                    perceptionWithComprehensionList.add(studentGraphNodesArray);

                    remainingPerceptionWithComprehensionMap.remove(percepWithCompString.getKey(), percepWithCompString.getValue());
                }
            }
        }

        for(TutorGraph each_tutor_graph : tutorGraphList)
        {
            if (each_tutor_graph.getActionNode().getActionNode().equals(currentAction))
            {
                for (Map.Entry<String, String> remaining_percepWithCompString : remainingPerceptionWithComprehensionMap.entrySet())
                {
                    for (TutorActionConditionDetails tutorACDetails : each_tutor_graph.getActionNode().getAcDetailsList()) {

                        Symbol tutorACExpSymbol = getExpSymbol(tutorACDetails.getExp());

                        if(remaining_percepWithCompString.getKey().equals(tutorACExpSymbol.toString()))
                        {
                            StudentGraphNode[] studentGraphNodesArray = new StudentGraphNode[2];

                            StudentGraphNode perceptionNode = new StudentGraphNode(GraphNodeConstants.NODE_TYPE_PERCEPTION);
                            StudentGraphNode comprehensionNode = new StudentGraphNode(GraphNodeConstants.NODE_TYPE_COMPREHENSION);

                            perceptionNode.setPerceptionExp(tutorACDetails.getExp());

                            studentGraphNodesArray[0] = perceptionNode;
                            studentGraphNodesArray[1] = comprehensionNode;

                            perceptionWithComprehensionList.add(studentGraphNodesArray);
                        }
                    }
                }
            }
        }

        return perceptionWithComprehensionList;
    }

    private static Exp getComprehensionExpFromHashMap(String comprehensionString, StudentGraph studentGraphObject)
    {
        Exp comprehensionExp = null;

        for (Map.Entry<String, Exp> predicateExp : studentGraphObject.getStateNode().getPredicateExpDataHashMap().entrySet()) {

            if(comprehensionString.equals(predicateExp.getKey()))
            {
                comprehensionExp = predicateExp.getValue();
            }
        }

        return comprehensionExp;
    }

    public static StudentGraph getCurrentStudentGraph()
    {
        return ApplicationController.studentGraphList.get( ApplicationController.studentGraphList.size()-1 );
    }

    public static void printAllStudentGraph()
    {
        System.err.println("----------------------------------- Student Graph -----------------------------------");
        for(StudentGraph each_student_graph : studentGraphList) {

            System.err.println("----------------------------------- Graph -----------------------------------");

            System.out.println("State ID: " + each_student_graph.getStateNode().getState().getS_id());
            System.err.println("   Perception & Comprehension");
            for (Map.Entry<String, String> percepWithComp : each_student_graph.getStateNode().getPerceptionWithComprehensionHashMap().entrySet()) {
                System.out.println("   Perception: " + percepWithComp.getKey()
                        + " -> Comprehension " + percepWithComp.getValue());
            }
            System.err.println("   Predicate & Exp");
            for (Map.Entry<String, Exp> predicateExpData : each_student_graph.getStateNode().getPredicateExpDataHashMap().entrySet()) {
                System.out.println("   Predicate: " + predicateExpData.getKey()
                        + " -> Exp " + predicateExpData.getValue());
            }
            if (!each_student_graph.getStateNode().getChildNodes().isEmpty()) {
                printChildStudentGraph(each_student_graph.getStateNode());
            }
            System.err.println("---------------------------------------------------------------------------");

            for (StudentGraphNode each_projection : each_student_graph.getPerceptionList()) {
                System.out.println("Perception: " + each_projection.getPerceptionExp());
                if (!each_projection.getChildNodes().isEmpty()) {

                    printChildStudentGraph(each_projection);

                }
                if(!each_projection.getParentNodes().isEmpty())
                {
                    printParentStudentGraph(each_projection);
                }
                System.err.println("---");
            }
            System.err.println("---------------------------------------------------------------------------");

            for (StudentGraphNode each_projection : each_student_graph.getComprehensionList()) {
                System.out.println("Comprehension: " + each_projection.getComprehensionExp());
                if (!each_projection.getChildNodes().isEmpty()) {

                    printChildStudentGraph(each_projection);

                }
                if(!each_projection.getParentNodes().isEmpty())
                {
                    printParentStudentGraph(each_projection);
                }
                System.err.println("---");
            }
            System.err.println("---------------------------------------------------------------------------");

            System.out.println("Action name: " + each_student_graph.getActionNode().getActionNode().getActionName());
            for(StudentActionConditionDetails acDetailsNode : each_student_graph.getActionNode().getAcDetailsList())
            {
                System.out.println("   Action Detail: Exp " + acDetailsNode.getExp());
                System.out.println("                  Type " + acDetailsNode.getType());
                System.out.println("                  RefGraphNode " + acDetailsNode.getRefGraphNode().getNodeType());
            }
            if (!each_student_graph.getActionNode().getChildNodes().isEmpty()) {

                printChildStudentGraph(each_student_graph.getActionNode());

            }
            if(!each_student_graph.getActionNode().getParentNodes().isEmpty())
            {
                printParentStudentGraph(each_student_graph.getActionNode());
            }
            System.err.println("---------------------------------------------------------------------------");

            for (StudentGraphNode each_action_condition : each_student_graph.getActionConditionList()) {
                System.out.println("Action Condition: " + each_action_condition.getActionCondition());

                for(StudentActionConditionDetails acDetailsNode : each_action_condition.getAcDetailsList())
                {
                    System.out.println("Action Detail: Exp " + acDetailsNode.getExp());
                    System.out.println("               Type " + acDetailsNode.getType());
//                    System.out.println("               RefGraphNode " + acDetailsNode.getRefGraphNode().getNodeType());
                }

                System.out.println("Perception & Comprehension");
                for (Map.Entry<String, String> percepWithComp : each_action_condition.getPerceptionWithComprehensionHashMap().entrySet()) {
                    System.out.println("   Perception: " + percepWithComp.getKey()
                            + " -> Comprehension " + percepWithComp.getValue());
                }

                System.out.println("Perception & Comprehension ArrayList");
                for(StudentGraphNode[] percepWithCompList : each_action_condition.getPerceptionWithComprehensionList())
                {
                    System.out.println("   Perception: " + percepWithCompList[0].getPerceptionExp()
                            + " -> Comprehension " + percepWithCompList[1].getComprehensionExp());
                }

                if (!each_action_condition.getChildNodes().isEmpty()) {

                    printChildStudentGraph(each_action_condition);

                }
                if(!each_action_condition.getParentNodes().isEmpty())
                {
                    printParentStudentGraph(each_action_condition);
                }
                System.err.println("---");
            }
            System.err.println("---------------------------------------------------------------------------");

            for (StudentGraphNode each_projection : each_student_graph.getProjectionList()) {
                System.out.println("Projection: " + each_projection.getProjectionExp());
                System.out.println("   is_desired_outcome: " + each_projection.isDesiredOutCome());
                System.out.println("   is_allow_explore: " + each_projection.isAllowExplore());
                if (!each_projection.getChildNodes().isEmpty()) {

                    printChildStudentGraph(each_projection);

                }
                if(!each_projection.getParentNodes().isEmpty())
                {
                    printParentStudentGraph(each_projection);
                }
                System.err.println("---");
            }
            System.err.println("---------------------------------------------------------------------------");

            System.out.println("End: ");
            System.out.println("   isSatisfyDesiredOutcome: " + each_student_graph.getEndNode().isSatisfyDesiredOutcome());
            System.out.println("   isInvalidAction: " + each_student_graph.getEndNode().isInvalidAction());
            if (!each_student_graph.getEndNode().getChildNodes().isEmpty()) {

                printChildStudentGraph(each_student_graph.getEndNode());

            }
            if(!each_student_graph.getEndNode().getParentNodes().isEmpty())
            {
                printParentStudentGraph(each_student_graph.getEndNode());
            }
            System.err.println("---------------------------------------------------------------------------");

            System.err.println("------------------------------------ End ---------------------------------------");
        }
    }

    public static void printChildStudentGraph(StudentGraphNode child_details) {
        for (StudentGraphNode child_node : child_details.getChildNodes()) {
            System.out.println("--------> child " + child_node.getNodeType());

            if (child_node.getNodeType().equals(GraphNodeConstants.NODE_TYPE_STATE)) {
                System.out.println("                State ID: " + child_node.getState().getS_id());
            } else if (child_node.getNodeType().equals(GraphNodeConstants.NODE_TYPE_ACTION)) {
                System.out.println("                Action name: " + child_node.getActionNode().getActionName());
            } else if (child_node.getNodeType().equals(GraphNodeConstants.NODE_TYPE_ACTION_CONDITION)) {
                System.out.println("                Action Condition: " + child_node.getActionCondition());
            } else if (child_node.getNodeType().equals(GraphNodeConstants.NODE_TYPE_PROJECTION)) {
                System.out.println("                Projection: " + child_node.getProjectionExp());
            } else if (child_node.getNodeType().equals(GraphNodeConstants.NODE_TYPE_END)) {
                System.out.println("                End: ");
                System.out.println("                isSatisfyDesiredOutcome: " + child_node.isSatisfyDesiredOutcome());
                System.out.println("                isInvalidAction: " + child_node.isInvalidAction());
            }
        }
    }

    public static void printParentStudentGraph(StudentGraphNode parent_details)
    {
        if (!parent_details.getParentNodes().isEmpty()) {
            for (StudentGraphNode parent_node : parent_details.getParentNodes()) {
                System.out.println("--------> parent " + parent_node.getNodeType());

                if (parent_node.getNodeType().equals(GraphNodeConstants.NODE_TYPE_STATE)) {
                    System.out.println("                State ID: " + parent_node.getState().getS_id());
                } else if (parent_node.getNodeType().equals(GraphNodeConstants.NODE_TYPE_ACTION)) {
                    System.out.println("                Action name: " + parent_node.getActionNode().getActionName());
                } else if (parent_node.getNodeType().equals(GraphNodeConstants.NODE_TYPE_ACTION_CONDITION)) {
                    System.out.println("                Action Condition: " + parent_node.getActionCondition());
                } else if (parent_node.getNodeType().equals(GraphNodeConstants.NODE_TYPE_PROJECTION)) {
                    System.out.println("                Projection: " + parent_node.getProjectionExp());
                } else if (parent_node.getNodeType().equals(GraphNodeConstants.NODE_TYPE_END)) {
                    System.out.println("                End: ");
                    System.out.println("                isSatisfyDesiredOutcome: " + parent_node.isSatisfyDesiredOutcome());
                    System.out.println("                isInvalidAction: " + parent_node.isInvalidAction());
                }
            }
        }
    }

}
