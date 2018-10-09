using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Optimization;

namespace WebApplication1.App_Start
{
    public class BundleConfig
    {
        public static void RegisterBundles(BundleCollection bundles)
        {
            bundles.Add(new StyleBundle("~/general/css").Include(
                        "~/Content/bootstrap.min.css/",
                        "~/Content/site.css"
                      ));

            bundles.Add(new ScriptBundle("~/general/js").Include(
                        "~/Scripts/jquery-1.10.2.min.js",
                        "~/Scripts/bootstrap.min.js"
                      ));

        }
    }
}