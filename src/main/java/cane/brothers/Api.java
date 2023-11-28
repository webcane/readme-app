package cane.brothers;

public interface Api {

  String API_PREF = "/api";
  String PATH_DEL = "/";

  interface Article {
    String NAME = "articles";
    String PATH = API_PREF + PATH_DEL + NAME;
  }

  interface Tag {
    String NAME = "tags";
    String PATH = API_PREF + PATH_DEL + NAME;
  }
}
