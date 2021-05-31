using System;
using System.ServiceModel;
using System.ServiceModel.Web;
using System.Collections.Generic;


namespace Partner.Service {

  [ServiceContract]
  public interface IWeatherService
    {
    [OperationContract]
    [WebInvoke( Method = "GET", UriTemplate = "weather",
                ResponseFormat = WebMessageFormat.Json)]
    Weather GetWeather();

}

}
