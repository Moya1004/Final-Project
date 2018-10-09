using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Web;
using WebApplication1.Interfaces;

namespace WebApplication1.Services
{
    public class FileService : IFileService
    {
        public string UploadFile(HttpPostedFileBase postedFile, string serverBaseAddress, string repoAddress, string[] allowedExtensions, int maxFileSize)
        {
            if (postedFile != null && allowedExtensions.Contains(Path.GetExtension(postedFile.FileName).ToLower()) && postedFile.ContentLength <= maxFileSize)
            {
                try
                {
                    string path = Path.Combine(HttpContext.Current.Request.PhysicalApplicationPath, repoAddress);
                    string sSource;
                    string sLog;
                    string sEvent;

                    sSource = "dotNET WebApplication";
                    sLog = "Application";
                    sEvent = "Sample Event";

                    if (!EventLog.SourceExists(sSource))
                        EventLog.CreateEventSource(sSource, sLog);

                    EventLog.WriteEntry(sSource, "Http.path: " + HttpContext.Current.Request.PhysicalApplicationPath+" path: "+ path , EventLogEntryType.Error, 234);
                    System.Diagnostics.Debug.WriteLine("This will be displayed in output window");
                    string fileName = Path.GetFileNameWithoutExtension(postedFile.FileName);
                    string extension = Path.GetExtension(postedFile.FileName);
                    if (!Directory.Exists(path))
                    {
                        Directory.CreateDirectory(path);
                    }
                    string fullFile = path + fileName + extension;
                    if (System.IO.File.Exists(fullFile))
                    {
                        int i = 1;
                        while (System.IO.File.Exists(fullFile))
                        {
                            fullFile = path + fileName + "(" + i + ")" + extension;
                            i++;
                        }
                    }
                    postedFile.SaveAs(fullFile);
                    FileInfo f = new FileInfo(fileName);
                    string fullpath = f.FullName;
                    return fullFile;
                }
                catch (Exception ex)
                {
                    EventLog.WriteEntry("dotNET WebApplication", ex.Message, EventLogEntryType.Error, 234);
                    return ex.Message;
                }
            }
            else
            {
                return "";
            }
        }

        public string MoveFile(string source, string destination, string fileName, bool deleteOldIfExists = false)
        {
            try
            {
                string sourceFile = source + fileName;
                string extension = Path.GetExtension(fileName);
                string destinationFile = destination + fileName;
                fileName = Path.GetFileNameWithoutExtension(fileName);
                if (!Directory.Exists(destination))
                {
                    Directory.CreateDirectory(destination);
                }
                if (System.IO.File.Exists(sourceFile))
                {
                    if (System.IO.File.Exists(destinationFile))
                    {
                        if (deleteOldIfExists)
                        {
                            System.IO.File.Delete(destinationFile);
                        }
                        else
                        {
                            int i = 1;
                            while (System.IO.File.Exists(destinationFile))
                            {
                                destinationFile = destination + fileName + "(" + i + ")" + extension;
                                i++;
                            }
                        }
                    }
                    System.IO.File.Move(sourceFile, destinationFile);
                }
                return Path.GetFileName(destinationFile);
            }
            catch
            {
                return "";
            }
        }

        public bool DeleteFile(string serverBaseAddress, string repoAddress, string source)
        {
            string fp = Path.Combine(serverBaseAddress, repoAddress) + "\\" + source;
            if (System.IO.File.Exists(fp))
            {
                System.IO.File.Delete(fp);
                return true;
            }
            return false;
        }
    }
}