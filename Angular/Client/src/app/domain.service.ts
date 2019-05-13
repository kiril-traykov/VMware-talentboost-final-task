import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Domain} from './Models/Domain';
import { Meme } from './Models/Meme';
@Injectable({
  providedIn: 'root'
})
export class DomainService {
domains : Domain[] = [];
memes : Meme[] = [];
address : String = "http://localhost:8080";
  constructor(private http : HttpClient) { }


  getDomains(){
    this.http.get(this.address +  "/domains").subscribe(
      data => this.extractDomains(data));
    }

    extractDomains(data){
      this.domains = [];
      for(let domain of data){
        this.domains.push(domain);
      }
    }

  getMemes(domain){
    this.http.get(this.address +  '/OtherMemes/' + domain).subscribe(
    data => this.extractMemes(data)
  );
  }

  extractMemes(data){
    this.memes = [];
    for(let meme of data){
      this.memes.push(meme);
    }
  }

  advancedFilter(filter : String){
    this.http.get(this.address + '/filter/' + filter).subscribe(
      data => this.extractMemes(data));
    }


  }



