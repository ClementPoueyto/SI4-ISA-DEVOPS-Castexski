using System;
using System.Net;
using System.ServiceModel;
using System.ServiceModel.Web;
using System.Collections.Generic;
using System.Linq;

namespace Partner.Service {

  // The service is stateful, as it is only a Proof of Concept.
  // Services should be stateless, this is for demonstration purpose only.
  [ServiceBehavior(InstanceContextMode = InstanceContextMode.Single)]
  public class WeatherService : IWeatherService
  {

    public Weather GetWeather()
    {
      var weather = new Weather();
      Array values = Enum.GetValues(typeof(WeatherStatus));
      Random random = new Random();
      if(Server.Weather == null){
        weather.Status = (WeatherStatus)values.GetValue(random.Next(values.Length));
      }else{
        weather.Status = (WeatherStatus) Enum.Parse(typeof(WeatherStatus), Server.Weather, true);
      }
      Console.WriteLine("Weather is "+weather.Status);

      return weather;
    }

  }
}
