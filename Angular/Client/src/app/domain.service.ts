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
  
constructor(private http : HttpClient) { }


  getDomains(){
    this.memes = [];
    this.http.get("/domains").subscribe(
      data => this.extractDomains(data));
    }

    extractDomains(domains){
      this.domains = [];
      for(let domain of domains){
        this.domains.push(domain);
      }
    }

  getMemes(domain){
    this.http.get('/OtherMemes/' + domain).subscribe(
    data => this.extractMemes(data)
  );
  }

  extractMemes(memes){
    this.memes = [];
    for(let meme of memes){
      this.memes.push(meme);
    }
  }

  advancedFilter(filter : String){
    this.http.get('/filter/' + filter).subscribe(
      data => this.extractMemes(data));
    }

  }



