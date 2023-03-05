import axios from "axios";

export function getAxiosDefaultParams() {
  let axiosParams = {};
  axiosParams.headers = {
    "Content-Type": "application/json",
    Authorization: localStorage.getItem("appToken") || "",
  };
  axiosParams.image = false;
  axiosParams.onUploadProgress = undefined;
  axiosParams.basePath = undefined;
  axiosParams.params = undefined;

  return axiosParams;
}

export async function sendRequest(method, data, url, axiosParams) {
  let config = {};
  if (axiosParams == undefined) {
    axiosParams = getAxiosDefaultParams();
  }
  if (method != "get") {
    let jsonData;
    if (!axiosParams.image) {
      jsonData = JSON.stringify(data);
    } else {
      jsonData = data;
    }

    config = createConfig(
      url,
      method,
      axiosParams.headers,
      axiosParams.params,
      jsonData,
      axiosParams.onUploadProgress,
      axiosParams.basePath
    );

    return handleRequest(config);
  } else {
    config = createConfig(
      url,
      method,
      axiosParams.headers,
      axiosParams.params,
      undefined,
      axiosParams.onUploadProgress,
      axiosParams.basePath
    );

    return handleRequest(config);
  }
}
function createConfig(
    url,
    method,
    headers,
    params,
    jsonData,
    onUploadProgress,
    basePath
  ) {
    let config = {};
    config = jsonData
      ? {
          method: method,
          headers: headers,
          params: params,
          data: jsonData,
          onUploadProgress,
        }
      : {
          method: method,
          headers: headers,
          params: params,
        };
    config.url = basePath ? process.env.VUE_APP_PUBLIC_URL + url : process.env.VUE_APP_PRIVATE_URL + url;
  
    return config;
  }
async function handleRequest(config) {
  try {
    const response = await axios(config);
    return response.data;
  } catch (error) {
    // store.dispatch("displayBanner", {
    //   message: "Réessayer plus tard",
    //   type: "error",
    //   activate: true,
    // });
    return error.response;
  }
}
