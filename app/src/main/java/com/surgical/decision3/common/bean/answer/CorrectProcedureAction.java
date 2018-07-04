package com.surgical.decision3.common.bean.answer;

public class CorrectProcedureAction
{
	int id;
	int domain_id;
	int procedure_id;
	int case_id;
	int subprocedure_id;
	int subprocedure_step_id;
	String subprocedure_step_name;
	String subprocedure_step;
	String subprocedure_step_code;
	String action;
	int prepare_set_id_1;
	String set_name_1;
	String set_code_1;
	int prepare_set_id_2;
	String set_name_2;
	String set_code_2;
	int tool_id;
	String tool_name;
	String tool_code;
	String tool_number;
	double tool_length;
	String entry_point;
	String direction;
	String drawing_shape;
	double drill_depth_from_surface;
	String material_id_1;
	String material_name_1;
	String material_code_1;
	String material_id_2;
	String material_name_2;
	String material_code_2;
	int total_loop;
	int current_loop;
	int starting_step_id;
	String starting_step_code;
	int ending_step_id;
	String ending_step_code;

	public String getDrawing_shape()
	{
		return drawing_shape;
	}

	public void setDrawing_shape(String drawing_shape)
	{
		this.drawing_shape = drawing_shape;
	}

	public String getTool_code()
	{
		return tool_code;
	}

	public void setTool_code(String tool_code)
	{
		this.tool_code = tool_code;
	}

	public String getSubprocedure_step_code()
	{
		return subprocedure_step_code;
	}

	public void setSubprocedure_step_code(String subprocedure_step_code)
	{
		this.subprocedure_step_code = subprocedure_step_code;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getDomain_id()
	{
		return domain_id;
	}

	public void setDomain_id(int domain_id)
	{
		this.domain_id = domain_id;
	}

	public int getProcedure_id()
	{
		return procedure_id;
	}

	public void setProcedure_id(int procedure_id)
	{
		this.procedure_id = procedure_id;
	}

	public int getCase_id()
	{
		return case_id;
	}

	public void setCase_id(int case_id)
	{
		this.case_id = case_id;
	}

	public int getSubprocedure_id()
	{
		return subprocedure_id;
	}

	public void setSubprocedure_id(int subprocedure_id)
	{
		this.subprocedure_id = subprocedure_id;
	}

	public int getSubprocedure_step_id()
	{
		return subprocedure_step_id;
	}

	public void setSubprocedure_step_id(int subprocedure_step_id)
	{
		this.subprocedure_step_id = subprocedure_step_id;
	}

	public String getSubprocedure_step_name()
	{
		return subprocedure_step_name;
	}

	public void setSubprocedure_step_name(String subprocedure_step_name)
	{
		this.subprocedure_step_name = subprocedure_step_name;
	}

	public String getSubprocedure_step()
	{
		return subprocedure_step;
	}

	public void setSubprocedure_step(String subprocedure_step)
	{
		this.subprocedure_step = subprocedure_step;
	}

	public String getAction()
	{
		return action;
	}

	public void setAction(String action)
	{
		this.action = action;
	}

	public int getPrepare_set_id_1()
	{
		return prepare_set_id_1;
	}

	public void setPrepare_set_id_1(int prepare_set_id)
	{
		this.prepare_set_id_1 = prepare_set_id;
	}

	public int getTool_id()
	{
		return tool_id;
	}

	public void setTool_id(int tool_id)
	{
		this.tool_id = tool_id;
	}

	public String getTool_name()
	{
		return tool_name;
	}

	public void setTool_name(String tool_name)
	{
		this.tool_name = tool_name;
	}

	public String getTool_number()
	{
		return tool_number;
	}

	public void setTool_number(String tool_number)
	{
		this.tool_number = tool_number;
	}

	public String getDirection()
	{
		return direction;
	}

	public void setDirection(String direction)
	{
		this.direction = direction;
	}

	public double getDrill_depth_from_surface()
	{
		return drill_depth_from_surface;
	}

	public void setDrill_depth_from_surface(double drill_depth_from_surface)
	{
		this.drill_depth_from_surface = drill_depth_from_surface;
	}

	public String getEntry_point()
	{
		return entry_point;
	}

	public void setEntry_point(String entry_point)
	{
		this.entry_point = entry_point;
	}

	public String getSet_name_1()
	{
		return set_name_1;
	}

	public void setSet_name_1(String set_name)
	{
		this.set_name_1 = set_name;
	}

	public String getSet_code_1()
	{
		return set_code_1;
	}

	public void setSet_code_1(String set_code)
	{
		this.set_code_1 = set_code;
	}

	public String getMaterial_id_1()
	{
		return material_id_1;
	}

	public void setMaterial_id_1(String material_id_1)
	{
		this.material_id_1 = material_id_1;
	}

	public String getMaterial_name_1()
	{
		return material_name_1;
	}

	public void setMaterial_name_1(String material_name_1)
	{
		this.material_name_1 = material_name_1;
	}

	public String getMaterial_code_1()
	{
		return material_code_1;
	}

	public void setMaterial_code_1(String material_code_1)
	{
		this.material_code_1 = material_code_1;
	}

	public String getMaterial_id_2()
	{
		return material_id_2;
	}

	public void setMaterial_id_2(String material_id_2)
	{
		this.material_id_2 = material_id_2;
	}

	public String getMaterial_name_2()
	{
		return material_name_2;
	}

	public void setMaterial_name_2(String material_name_2)
	{
		this.material_name_2 = material_name_2;
	}

	public String getMaterial_code_2()
	{
		return material_code_2;
	}

	public void setMaterial_code_2(String material_code_2)
	{
		this.material_code_2 = material_code_2;
	}

	public int getTotal_loop()
	{
		return total_loop;
	}

	public void setTotal_loop(int total_loop)
	{
		this.total_loop = total_loop;
	}

	public int getCurrent_loop()
	{
		return current_loop;
	}

	public void setCurrent_loop(int current_loop)
	{
		this.current_loop = current_loop;
	}

	public int getStarting_step_id()
	{
		return starting_step_id;
	}

	public void setStarting_step_id(int starting_step_id)
	{
		this.starting_step_id = starting_step_id;
	}

	public String getStarting_step_code()
	{
		return starting_step_code;
	}

	public void setStarting_step_code(String starting_step_code)
	{
		this.starting_step_code = starting_step_code;
	}

	public int getEnding_step_id()
	{
		return ending_step_id;
	}

	public void setEnding_step_id(int ending_step_id)
	{
		this.ending_step_id = ending_step_id;
	}

	public String getEnding_step_code()
	{
		return ending_step_code;
	}

	public void setEnding_step_code(String ending_step_code)
	{
		this.ending_step_code = ending_step_code;
	}

	public int getPrepare_set_id_2()
	{
		return prepare_set_id_2;
	}

	public void setPrepare_set_id_2(int prepare_set_id_2)
	{
		this.prepare_set_id_2 = prepare_set_id_2;
	}

	public String getSet_name_2()
	{
		return set_name_2;
	}

	public void setSet_name_2(String set_name_2)
	{
		this.set_name_2 = set_name_2;
	}

	public String getSet_code_2()
	{
		return set_code_2;
	}

	public void setSet_code_2(String set_code_2)
	{
		this.set_code_2 = set_code_2;
	}

	public double getTool_length()
	{
		return tool_length;
	}

	public void setTool_length(double tool_length)
	{
		this.tool_length = tool_length;
	}

	
}
