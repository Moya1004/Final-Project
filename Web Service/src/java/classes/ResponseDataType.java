package classes;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import model.MethodModel;
import model.TestResultModel;
import model.VariableModel;

@XmlRootElement(name = "responsedatatype")
public class ResponseDataType {
	
        private String fileName;
        private MethodModel[] methods;
        private VariableModel[] variables;
        private int NumberOfTestsWritten;
        private String testClassName;
        private double successRate;
        private String Other_details;
        private String[] failureMessages;


    @XmlAttribute
    public String getFileName() {
        return fileName;
    }
    
    public ResponseDataType(String fileName,MethodModel[] methods,VariableModel[] variables,int NumberOfTestsWritten, String testClassName , String[] failureMessages,double successRate, String Other_details)
    {
        this.variables = variables;
        this.fileName = fileName;
        this.methods = methods;
        this.NumberOfTestsWritten = NumberOfTestsWritten;
        this.testClassName = testClassName;
        this.successRate = successRate;
        this.failureMessages = failureMessages;
        this.Other_details = Other_details;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    @XmlElement
    public MethodModel[] getMethods() {
        return methods;
    }

    public void setMethods(MethodModel[] methods) {
        this.methods = methods;
    }
    
    @XmlElement
    public VariableModel[] getVariables() {
        return variables;
    }

    public void setVariables(VariableModel[] variables) {
        this.variables = variables;
    }
    
    @XmlElement
    public int getNumberOfTestsWritten() {
        return NumberOfTestsWritten;
    }

    public void setNumberOfTestsWritten(int NumberOfTestsWritten) {
        this.NumberOfTestsWritten = NumberOfTestsWritten;
    }
    @XmlElement
    public String getTestClassName() {
        return testClassName;
    }

    public void setNumberOfTestsNotWritten(String testClassName) {
        this.testClassName = testClassName;
    }
    @XmlElement
    public double getSuccessRate() {
        return successRate;
    }

    public void setSuccessRate(double successRate) {
        this.successRate = successRate;
    }
    
    @XmlElement
    public String[] getFailureMessages() {
        return this.failureMessages;
    }

    public void setFailureMessages(String[] failureMessages) {
        this.failureMessages = failureMessages;
    }
    @XmlElement
    public String getOther_details() {
        return Other_details;
    }

    public void setOther_details(String Other_details) {
        this.Other_details = Other_details;
    }
        
	
} 