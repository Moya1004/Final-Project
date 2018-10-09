using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace WebApplication1.Models
{
    public class ResponseModel
    {
        public String fileName { get; set; }
        public MethodModel[] methods { get; set; }
        public int NumberOfTestsWritten { get; set; }
        public string testClassName { get; set; }
        public string[] failureMessages { get; set; }
        public double successRate { get; set; }
        public String Other_details { get; set; }
        public VariableModel[] variables { get; set; }
    }
}