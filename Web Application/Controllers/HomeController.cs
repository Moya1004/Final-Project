using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Configuration;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Net.Sockets;
using System.Web;
using System.Web.Mvc;
using System.Web.Script.Serialization;
using WebApplication1;
using WebApplication1.Models;
using WebApplication1.Services;

namespace WebApplication1.Controllers
{
    public class HomeController : Controller
    {

        private static List<ResponseModel> list = null;
        // GET: Home
        public ActionResult Index()
        {
            //ServiceReference1.CalculateClient proxy = new ServiceReference1.CalculateClient();
            //var xxx = proxy.addition(4, 6);
            //var fileNma = proxy.ReadFile("this");
            return View();
        }

        [ValidateAntiForgeryToken]
        [HttpPost]
        public ActionResult Index(HttpPostedFileBase filePosted)
        {
            //FileService fileService = new FileService();
            //MemoryStream target = new MemoryStream();
            //filePosted.InputStream.CopyTo(target);
            //byte[] data = target.ToArray();
            //ServiceReference1.CalculateClient proxy = new ServiceReference1.CalculateClient();
            //string fileName = filePosted.FileName;
            //string fileExt = Path.GetExtension(fileName);
            //long fileSize = filePosted.ContentLength;
            //string[] allowedFileExtension = { ".java", ".zip" };
            //string message = fileService.UploadFile(filePosted, ConfigurationManager.AppSettings["FileRepositoryBaseVirtualPath"], ConfigurationManager.AppSettings["FileRepositoryPath"], allowedFileExtension,1000000000);
            //ViewBag.Result = message;
            //if (message.EndsWith(".zip"))
            //{
            //string startPath = ConfigurationManager.AppSettings["FileRepositoryPath"] +@"\start";
            //string zipPath = message;
            //string extractPath = ConfigurationManager.AppSettings["FileRepositoryPath"];

            //System.IO.Compression.ZipFile.CreateFromDirectory(startPath, zipPath);
            //System.IO.Compression.ZipFile.ExtractToDirectory(zipPath, @"C:\Users\Moya\source\repos\WebApplication1\WebApplication1\FileRepository\Excracted\");
            //proxy.CalculateClass("", null);
            //}
            string fileName = filePosted.FileName;
            MemoryStream target = new MemoryStream();
            filePosted.InputStream.CopyTo(target);
            byte[] fileByteArray = target.ToArray();
            ServiceReference1.CalculateClient proxy = new ServiceReference1.CalculateClient();
            TcpClient Client = new TcpClient();
            try { Client.Connect("localhost", 9090); }
            catch(Exception ex) { }
            string response = proxy.CalculateClass(fileName, fileByteArray);
            ResponseModel[] results = null;
            list = new List<ResponseModel>();
            try
            {
                results = JsonConvert.DeserializeObject<ResponseModel[]>(response);
            }
            catch (Exception ex)
            {
                TempData["Result"] = null;
                string result = ex.Message;
                result = result + " ";
            }
            if (results != null)
            {
                list = results.ToList<ResponseModel>();
                Session["Response"] = list;
            }
            return RedirectToAction("Result");
        }
        // GET: Result
        public ActionResult Result()
        {
            return View(list);
        }

        public ActionResult Details(string name)
        {
            ResponseModel model = null;
            if (list != null)
                model = list.Where(x => x.fileName == name).FirstOrDefault();
            return View(model);
        }

        public FileStreamResult ShowFile(string filePath)
        {
            if (!String.IsNullOrEmpty(filePath))
            {
                var fileStream = new FileStream(filePath,
                                                FileMode.Open
                                            );
                return File(fileStream, "text/plain");
            }
            else
                return null;

        }

        public ActionResult DisplayPDF(string filePath)
        {
            FileStream fs = new FileStream(filePath, FileMode.Open, FileAccess.Read);
            int length = Convert.ToInt32(fs.Length);
            byte[] data = new byte[length];
            fs.Read(data, 0, length);
            fs.Close();
            MemoryStream pdfStream = new MemoryStream();
            pdfStream.Write(data, 0, data.Length);
            pdfStream.Position = 0;
            return new FileStreamResult(pdfStream, "application/pdf");
        }

        
    }
}