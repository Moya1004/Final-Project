using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace WebApplication1.Interfaces
{
    public interface IFileService
    {
        string UploadFile(HttpPostedFileBase postedFile, string serverBaseAddress, string repoAddress, string[] allowedExtensions, int maxFileSize);
        string MoveFile(string source, string destination, string fileName, bool deleteOldIfExists = false);
        bool DeleteFile(string serverBaseAddress, string repoAddress = "", string source="");
    }
}