package ${pkg};

<#if (typeSet?seq_contains("Date"))>
    import java.util.Date;
</#if>
<#if (typeSet?seq_contains("BigDecimal"))>
    import java.math.BigDecimal;
</#if>
<#list typeSet as en>
    <#if en?item_parity?contains("List")>
        import java.util.List;
        <#break>
    </#if>
</#list>

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
<#--表名-->
public class ${tableName} {

<#--字段-->
<#list dataList as filed>
    /** ${filed.filedMsg} */
    private ${filed.type} ${filed.filedName};

</#list>
<#--get和set-->
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
<#list subGenDataList as subGenData>
    /**
    * ${subGenData.tableMsg}
    *
    */
    <#if (subGenData.lombok?boolean)>
        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
    </#if>
<#--表名-->
    public static class ${subGenData.tableName} {

<#--字段-->
    <#list subGenData.dataList as subFiled>
        /** ${subFiled.filedMsg} */
        private ${subFiled.type} ${subFiled.filedName};

    </#list>

<#--get和set-->
    <#if (subGenData.lombok == "false")>
        <#list subGenData.dataList as subFiled>
            public ${subFiled.type} get${subFiled.filedName?cap_first}(){
            return this.${subFiled.filedName};
            }

            public void set${subFiled.filedName?cap_first}(${subFiled.type} ${subFiled.filedName}){
            this.${subFiled.filedName} = ${subFiled.filedName};
            }

        </#list>
    </#if>
    }
</#list>
}