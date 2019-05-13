import { Injectable } from '@angular/core';
import {Meme} from './Models/Meme';
import {HttpClient} from '@angular/common/http'
@Injectable({
  providedIn: 'root'
})
export class MemeService {
public index : number = -1;
public apiIndex : number = -1;
public apiUrl : String = 'http://localhost:8080';
public memes : Meme[] = [];
  constructor(private http : HttpClient) { }

public getMemes(page : Number){
this.memes = [];
this.http.get(this.apiUrl + '/memes/' + page).subscribe(
  data => this.extractMemes(data));

}

public add(fd : FormData){
  console.log("Service Reached");
  this.http.post(this.apiUrl + '/add',fd).subscribe();

}

public delete(i : Number){
  this.http.delete(this.apiUrl + '/delete/' + i).subscribe();
}

public editMemeSetUp(i,index)
{
 this.index = i;
 this.apiIndex = index;

}
public updateTitle(title : String){
  console.log("UpdateTitle");
  this.http.put('http://localhost:8080/updateTitle/' + this.apiIndex,title).subscribe();
}
public updateMeme(fd : FormData){
  this.http.put('http://localhost:8080/update/' + this.apiIndex , fd).subscribe();
}
finishEditMeme()
{
  this.index = -1;
  this.apiIndex = -1;
}

 private extractMemes(memes){
  for (let meme of memes) {
    this.memes.push(meme);
    }
 }

}
