package com.surgical.decision3.common.bean.studentaction;

/**
 * Created by Nattapon on 24/5/2560.
 */

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;


@Root
public class StudentAction {

    //LT1
    @Attribute(required = false)
    String rc_depth_to_current_file;

    //MI1
    @Attribute(required = false)
    String size_diff_incremental;
    @Attribute(required = false)
    String initial_tool_size;
    @Attribute(required = false)
    String final_tool_size;

    //MI2
    @Attribute(required = false)
    String rc_drill_depth;

    //MI4
    @Attribute(required = false)
    String tool_size_func;

    //TMC1
    @Attribute(required = false)
    String mc_size;

    //Press and cut cone
    @Attribute(required = false)
    String cut_cones_depth;

    @Attribute(required = false)
    String subprocedure_step_code;

    @Attribute(required = false)
    String action;

    @Attribute(required = false)
    String drill_depth_from_surface;

    @Attribute(required = false)
    String entry_point;

    @Attribute(required = false)
    String direction;

    @Attribute(required = false)
    String drawing_shape;

    @ElementList(required = false)
    ArrayList<StudentTool> tool_list;

    @Attribute(required = false)
    Boolean is_coated_with_cement;

    @Attribute(required = false)
    Boolean is_rb_tool_selected;

    @Attribute(required = false)
    String la_solution_type;

    @Attribute(required = false)
    String la_solution_amt;

    public String getLa_solution_amt()
    {
        return la_solution_amt;
    }

    public void setLa_solution_amt(String la_solution_amt)
    {
        this.la_solution_amt = la_solution_amt;
    }

    @Attribute(required = false)
    String tool_size;

    @Attribute(required = false)
    String rb_sheet_pos;

    @Attribute(required = false)
    String landmark;

    @Attribute(required = false)
    String tool_length;

    @Attribute(required = false)
    String reference_tooth_no;

    @Attribute(required = false)
    String remain_depth;

    @Attribute(required = false)
    String retract_depth;

    @Attribute(required = false)
    String solution_amount;

    @Attribute(required = false)
    String target_numb;

    public String getLa_solution_type() {
        return la_solution_type;
    }

    public void setLa_solution_type(String la_solution_type) {
        this.la_solution_type = la_solution_type;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getTool_length() {
        return tool_length;
    }

    public void setTool_length(String tool_length) {
        this.tool_length = tool_length;
    }

    public String getReference_tooth_no() {
        return reference_tooth_no;
    }

    public void setReference_tooth_no(String reference_tooth_no) {
        this.reference_tooth_no = reference_tooth_no;
    }

    public String getRemain_depth() {
        return remain_depth;
    }

    public void setRemain_depth(String remain_depth) {
        this.remain_depth = remain_depth;
    }

    public String getRetract_depth() {
        return retract_depth;
    }

    public void setRetract_depth(String retract_depth) {
        this.retract_depth = retract_depth;
    }

    public String getSolution_amount() {
        return solution_amount;
    }

    public void setSolution_amount(String solution_amount) {
        this.solution_amount = solution_amount;
    }

    public String getTargetNumb() {
        return target_numb;
    }

    public void setTargetNumb(String target_numb) {
        this.target_numb = target_numb;
    }

    public String getRb_sheet_pos() {
        return rb_sheet_pos;
    }

    public void setRb_sheet_pos(String rb_sheet_pos) {
        this.rb_sheet_pos = rb_sheet_pos;
    }

    public String getTool_Size(){ return tool_size; }
    public void setTool_Size(String tool_size)
    {
        this.tool_size = tool_size;
    }

    public String getSubprocedure_step_code(){ return subprocedure_step_code; }
    public void setSubprocedure_step_code(String subprocedure_step_code)
    {
        this.subprocedure_step_code = subprocedure_step_code;
    }

    public String getAction(){return action;}
    public void setAction(String action)
    {
        this.action = action;
    }

    public String getDrill_depth_from_surface(){return drill_depth_from_surface;}
    public void setDrill_depth_from_surface(String drill_depth_from_surface)
    {
        this.drill_depth_from_surface = drill_depth_from_surface;
    }

    public String getEntry_Point(){return entry_point;}
    public void setEntry_Point(String entry_point)
    {
        this.entry_point = entry_point;
    }

    public String getDirection(){return direction;}
    public void setDirection(String direction)
    {
        this.direction = direction;
    }

    public String getDrawing_Shape(){return drawing_shape;}
    public void setDrawing_Shape(String drawing_shape)
    {
        this.drawing_shape = drawing_shape;
    }

    public ArrayList<StudentTool> getTool_list(){return tool_list;}
    public void setTool_List(ArrayList<StudentTool> tool_list)
    {
        this.tool_list = tool_list;
    }

    public Boolean getIs_Coated_With_Cement(){return is_coated_with_cement;}
    public void setIs_Coated_With_Cement(Boolean is_coated_with_cement)
    {
        this.is_coated_with_cement = is_coated_with_cement;
    }

    public Boolean getIs_Rb_Tool_Selected(){return is_rb_tool_selected;}
    public void setIs_Rb_Tool_Selected(Boolean is_rb_tool_selected)
    {
        this.is_rb_tool_selected = is_rb_tool_selected;
    }

    //LT1
    public String getRcDepthToCurrentFile(){return rc_depth_to_current_file;}
    public void setRcDepthToCurrentFile(String rc_depth_to_current_file)
    {
        this.rc_depth_to_current_file = rc_depth_to_current_file;
    }

    //MI1
    public String getSizeDiffIncremental(){return size_diff_incremental;}
    public void setSizeDiffIncremental(String size_diff_incremental) {this.size_diff_incremental = size_diff_incremental;}
    public String getInitialToolSize(){return initial_tool_size;}
    public void setInitialToolSize(String initial_tool_size) {this.initial_tool_size = initial_tool_size;}
    public String getFinalToolSize(){return final_tool_size;}
    public void setFinalToolSize(String final_tool_size) {this.final_tool_size = final_tool_size;}

    //MI2
    public String getRcDrillDepth(){return rc_drill_depth;}
    public void setRcDrillDepth(String rc_drill_depth) {this.rc_drill_depth = rc_drill_depth;}

    //MI4
    public String getToolSizeFunc(){return tool_size_func;}
    public void setToolSizeFunc(String tool_size_func) {this.tool_size_func = tool_size_func;}

    //TMC1
    public String getMCSize(){return mc_size;}
    public void setMCSize(String mc_size) {this.mc_size = mc_size;}

    //Press and cut cones
    public String getCutConesDepth(){return cut_cones_depth;}
    public void setCutConesDepth(String cut_cones_depth) {this.cut_cones_depth = cut_cones_depth;}
}
