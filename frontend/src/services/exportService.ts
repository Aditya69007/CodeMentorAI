import api from "./api";

export const downloadDeveloperReport = async () => {
  const response = await api.get("/export/report/pdf", {
    responseType: "blob",
  });

  const blob = new Blob([response.data], {
    type: "application/pdf",
  });

  const url = window.URL.createObjectURL(blob);

  const link = document.createElement("a");
  link.href = url;
  link.download = "Developer_Report.pdf";

  document.body.appendChild(link);
  link.click();

  link.remove();
  window.URL.revokeObjectURL(url);
};