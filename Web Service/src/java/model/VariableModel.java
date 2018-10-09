/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Moya
 */
public class VariableModel {
    private String name;
    private String type;
    private String modifier;

    public VariableModel(String name, String type, String modifer) {
        this.name = name;
        this.type = type;
        this.modifier = modifer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModifer() {
        return modifier;
    }

    public void setModifer(String scope) {
        this.modifier = scope;
    }
}
