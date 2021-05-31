using System.Runtime.Serialization;
using System;

namespace Partner.Service {

  [DataContract(Namespace = "http://partner/external/weather/data/",
                Name = "weather")]
  public class Weather
  {

    [DataMember]
    public WeatherStatus Status { get; set; }

  }

  public enum WeatherStatus { Sun, Poudreuse, Avalanche }

}
