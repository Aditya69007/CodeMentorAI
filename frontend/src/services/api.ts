import axios, {
  AxiosError,
  type InternalAxiosRequestConfig,
} from "axios";


// ==================================================
// API INSTANCE
// ==================================================

const api = axios.create({

  baseURL:
    import.meta.env.VITE_API_BASE_URL
    || "http://localhost:8080/api/v1",

  headers: {
    "Content-Type": "application/json",
  },

  timeout: 30000,

});


// ==================================================
// REQUEST INTERCEPTOR
// ==================================================

api.interceptors.request.use(

  (
    config: InternalAxiosRequestConfig
  ) => {

    const token =
      localStorage.getItem("token");


    if (token) {

      config.headers.Authorization =
        `Bearer ${token}`;

    }


    return config;

  },

  (error: AxiosError) => {

    return Promise.reject(error);

  }

);


// ==================================================
// RESPONSE INTERCEPTOR
// ==================================================

api.interceptors.response.use(

  (response) => {

    return response;

  },

  (error: AxiosError) => {


    // ==================================================
    // NETWORK ERROR
    // ==================================================

    if (!error.response) {

      console.error(
        "Network error:",
        error.message
      );

      return Promise.reject(error);

    }


    const status =
      error.response.status;


    const requestUrl =
      error.config?.url ?? "";


    // ==================================================
    // UNAUTHORIZED
    // ==================================================

    if (status === 401) {

      console.error(
        "Unauthorized API request:",
        requestUrl,
        error.response.data
      );


      /*
       * IMPORTANT
       *
       * Do NOT automatically delete the token
       * whenever any API returns 401.
       *
       * Otherwise one failed feature request
       * logs the entire user out.
       */


      const isAuthenticationCheck =
        requestUrl.includes("/auth/me");


      if (isAuthenticationCheck) {

        localStorage.removeItem("token");

        localStorage.removeItem("user");

        localStorage.removeItem("role");


        const currentPath =
          window.location.pathname;


        const isPublicPage =
          currentPath === "/login"
          || currentPath === "/register";


        if (!isPublicPage) {

          window.location.replace("/login");

        }

      }

    }


    // ==================================================
    // FORBIDDEN
    // ==================================================

    if (status === 403) {

      console.error(
        "Access forbidden:",
        requestUrl,
        error.response.data
      );

    }


  // ==================================================
  // NOT FOUND
  // ==================================================

  if (status === 404) {

    const requestUrl =
      error.config?.url ?? "";


    // A new submission normally has no AI analysis
    // until the user clicks "Ask AI Mentor".
    // Therefore this 404 is expected.

    const isMissingAiAnalysis =
      requestUrl.startsWith(
        "/ai-mentor/analysis/"
      );


    if (!isMissingAiAnalysis) {

      console.warn(
        "API resource not found:",
        requestUrl,
        error.response.data
      );

    }

  }

    // ==================================================
    // SERVER ERROR
    // ==================================================

    if (status >= 500) {

      console.error(
        "Server error:",
        requestUrl,
        error.response.data
      );

    }


    return Promise.reject(error);

  }

);


// ==================================================
// EXTRACT API ERROR MESSAGE
// ==================================================

export const getApiErrorMessage = (

  error: unknown,

  fallbackMessage =
    "Something went wrong. Please try again."

): string => {


  if (axios.isAxiosError(error)) {

    const responseData =
      error.response?.data;


    if (

      responseData

      && typeof responseData === "object"

      && "message" in responseData

      && typeof responseData.message === "string"

    ) {

      return responseData.message;

    }


    if (!error.response) {

      return "Unable to connect to the server.";

    }

  }


  if (error instanceof Error) {

    return error.message;

  }


  return fallbackMessage;

};


export default api;