package com.surgical.decision3.common.bean.answer;

public class ErrorPrompt
{
	
	/*
	 	// error_prompt
			+ "`remark` TEXT); ";
	 * */
	int id;
	int domain_id;
	int procedure_id;
	int case_id;
	int subprocedure_id;
	int subprocedure_step_id;
	String subprocedure_step_name;
	String subprocedure_step_code;
	String situation;	//new
	String correct_tool;
	String error_code;
	String error_selected_tool;
	String error_outcome;
	String strategy_code1;
	String strategy_img1;
	String strategy_msg1;
	String strategy_exp1;
	String strategy_code2;
	String strategy_img2;
	String strategy_msg2;
	String strategy_exp2;
	String remark;	//new

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

	public String getCorrect_tool()
	{
		return correct_tool;
	}

	public void setCorrect_tool(String correct_tool)
	{
		this.correct_tool = correct_tool;
	}

	public String getError_code()
	{
		return error_code;
	}

	public void setError_code(String error_code)
	{
		this.error_code = error_code;
	}

	public String getError_selected_tool()
	{
		return error_selected_tool;
	}

	public void setError_selected_tool(String error_selected_tool)
	{
		this.error_selected_tool = error_selected_tool;
	}

	public String getError_outcome()
	{
		return error_outcome;
	}

	public void setError_outcome(String error_outcome)
	{
		this.error_outcome = error_outcome;
	}

	public String getStrategy_code1()
	{
		return strategy_code1;
	}

	public void setStrategy_code1(String strategy_code1)
	{
		this.strategy_code1 = strategy_code1;
	}

	public String getStrategy_img1()
	{
		return strategy_img1;
	}

	public void setStrategy_img1(String strategy_img1)
	{
		this.strategy_img1 = strategy_img1;
	}

	public String getStrategy_msg1()
	{
		return strategy_msg1;
	}

	public void setStrategy_msg1(String strategy_msg1)
	{
		this.strategy_msg1 = strategy_msg1;
	}

	public String getStrategy_exp1()
	{
		return strategy_exp1;
	}

	public void setStrategy_exp1(String strategy_exp1)
	{
		this.strategy_exp1 = strategy_exp1;
	}

	public String getStrategy_code2()
	{
		return strategy_code2;
	}

	public void setStrategy_code2(String strategy_code2)
	{
		this.strategy_code2 = strategy_code2;
	}

	public String getStrategy_img2()
	{
		return strategy_img2;
	}

	public void setStrategy_img2(String strategy_img2)
	{
		this.strategy_img2 = strategy_img2;
	}

	public String getStrategy_msg2()
	{
		return strategy_msg2;
	}

	public void setStrategy_msg2(String strategy_msg2)
	{
		this.strategy_msg2 = strategy_msg2;
	}

	public String getStrategy_exp2()
	{
		return strategy_exp2;
	}

	public void setStrategy_exp2(String strategy_exp2)
	{
		this.strategy_exp2 = strategy_exp2;
	}

	public String getSituation()
	{
		return situation;
	}

	public void setSituation(String situation)
	{
		this.situation = situation;
	}

	public String getRemark()
	{
		return remark;
	}

	public void setRemark(String remark)
	{
		this.remark = remark;
	}
	
}
