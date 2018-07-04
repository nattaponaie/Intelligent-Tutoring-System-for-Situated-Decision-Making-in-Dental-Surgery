package kb.common.datastructure.oneofprojection;

import java.lang.reflect.Array;
import java.util.ArrayList;

import kb.common.datastructure.graph.StudentGraphNode;
import kb.common.datastructure.graph.TutorGraph;
import kb.common.datastructure.graph.TutorGraphNode;

/**
 * Created by mess on 8/20/2017.
 * A class to identify group of ONEOF effects
 *
 */

public class OneOfGroup {
    ArrayList<OneOfNode> oneOfNode;
    ArrayList<TutorGraphNode> oneOfTutorGraphNode;
    ArrayList<StudentGraphNode> oneOfStudentGraphNode;

    public OneOfGroup(){
        oneOfNode = new ArrayList<>();
        oneOfTutorGraphNode = new ArrayList<>();
        oneOfStudentGraphNode = new ArrayList<>();
    }

    public ArrayList<OneOfNode> getOneOfNode() {
        return oneOfNode;
    }

    public void setOneOfNode(ArrayList<OneOfNode> oneOfNode) {
        this.oneOfNode = oneOfNode;
    }

    public void addOneOfNode(OneOfNode oneOfNode){
        this.oneOfNode.add(oneOfNode);
    }

    public ArrayList<TutorGraphNode> getOneOfTutorGraphNode() {
        return oneOfTutorGraphNode;
    }

    public void setOneOfTutorGraphNode(ArrayList<TutorGraphNode> oneOfTutorGraphNode) {
        this.oneOfTutorGraphNode = oneOfTutorGraphNode;
    }

    public void addOneOfTutorGraphNode(TutorGraphNode tutorGraphNode){
        this.oneOfTutorGraphNode.add(tutorGraphNode);
    }

    public ArrayList<StudentGraphNode> getOneOfStudentGraphNode() {
        return oneOfStudentGraphNode;
    }

    public void setOneOfStudentGraphNode(ArrayList<StudentGraphNode> oneOfStudentGraphNode) {
        this.oneOfStudentGraphNode = oneOfStudentGraphNode;
    }

    public void addOneOfStudentGraphNode(StudentGraphNode studentGraphNode){
        this.oneOfStudentGraphNode.add(studentGraphNode);
    }
}
