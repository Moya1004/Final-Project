using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace WebApplication1.Models
{
    public class MethodModel
    {
        public string methodName { get; set; }
        public string returnType { get; set; }
        public string[] parameters { get; set; }
        public string[] annoationTypes { get; set; }
    }
}