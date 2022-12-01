package ${pkg};

<#if (typeSet?seq_contains("Date"))>
import java.util.Date;
</#if>
<#if (typeSet?seq_contains("BigDecimal"))>
import java.math.BigDecimal;
</#if>

/**
* ${tableMsg}
*
* @author ${author}
* @date ${date?string("yyyy/MM/dd")}
*/
<#if (lombok?boolean)>
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
</#if>
public class ${tableName} {

<#list dataList as filed>
    /** ${filed.filedMsg} */
    private ${filed.type} ${filed.filedName};

</#list>

<#if (lombok == "false")>
    <#list dataList as filed>
    public ${filed.type} get${filed.filedName?cap_first}(){
    return this.${filed.filedName};
    }

    public void set${filed.filedName?cap_first}(${filed.type} ${filed.filedName}){
    this.${filed.filedName} = ${filed.filedName};
    }

    </#list>
</#if>
}