package com.nhom4.vanphongphamonline.controller;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Arrays;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nhom4.vanphongphamonline.services.ServiceStatus;

@Controller
public class DiaChiController {
	@ResponseBody
	@GetMapping(value = "api/diachi/thanhpho")
	public ResponseEntity<ServiceStatus> getAllCity() throws org.json.simple.parser.ParseException, ParseException {
		String json = null;
	    try {
	        JSONParser parser = new JSONParser();
	        //Use JSONObject for simple JSON and JSONArray for array of JSON.
	        JSONArray data = (JSONArray) parser.parse(
	              new FileReader(new File(getClass().getClassLoader().getResource("data/address/city.json").getFile())));//path to the JSON file.

	        json = data.toJSONString();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		return new ResponseEntity<ServiceStatus>(new ServiceStatus(0, json), HttpStatus.OK);
	}
	@ResponseBody
	@PostMapping(value = "api/diachi/quanhuyen")
	public ResponseEntity<ServiceStatus> getDistrictByCity(@RequestBody String id) throws org.json.simple.parser.ParseException, ParseException {
		String json = null;
	    try {
	        JSONParser parser = new JSONParser();
	        //Use JSONObject for simple JSON and JSONArray for array of JSON.
	        JSONArray data = (JSONArray) parser.parse(
	              new FileReader(new File(getClass().getClassLoader().getResource("data/address/district/district" + id + ".json").getFile())));//path to the JSON file.

	        json = data.toJSONString();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		return new ResponseEntity<ServiceStatus>(new ServiceStatus(0, json), HttpStatus.OK);
	}
	@ResponseBody
	@PostMapping(value = "api/diachi/thitran")
	public ResponseEntity<ServiceStatus> getWardByDistrict(@RequestBody String id) throws org.json.simple.parser.ParseException, ParseException {
		if(Integer.parseInt(id) > 6) {
			return new ResponseEntity<ServiceStatus>(new ServiceStatus(1, "Chưa hỗ trợ khu vực này"), HttpStatus.OK);
		}
		String json = null;
	    try {
	        JSONParser parser = new JSONParser();
	        //Use JSONObject for simple JSON and JSONArray for array of JSON.
	        JSONArray data = (JSONArray) parser.parse(
	              new FileReader(new File(getClass().getClassLoader().getResource("data/address/ward/ward" + id + ".json").getFile())));//path to the JSON file.

	        json = data.toJSONString();
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
		return new ResponseEntity<ServiceStatus>(new ServiceStatus(0, json), HttpStatus.OK);
	}
}
