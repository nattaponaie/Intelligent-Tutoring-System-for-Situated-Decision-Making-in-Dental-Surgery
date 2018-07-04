package com.surgical.decision3.common.bean.studentaction;

/**
 * Created by Nattapon on 29/5/2560.
 */

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root
public class StudentTool {

    @Attribute(required = false)
    String tool_type;

//    @Attribute(required = false)
//    String tool_size;

    public String getTool_Type(){ return tool_type; }
    public void setTool_Type(String tool_type)
    {
        this.tool_type = tool_type;
    }

//    public String getTool_Size(){ return tool_size; }
//    public void setTool_Size(String tool_size)
//    {
//        this.tool_size = tool_size;
//    }

}
