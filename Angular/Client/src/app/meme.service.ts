import { Injectable } from '@angular/core';
import {Meme} from './Models/Meme';
import {HttpClient} from '@angular/common/http'
@Injectable({
  providedIn: 'root'
})
export class MemeService {

public index : number = -1;
public apiIndex : number = -1;
public memes : Meme[] = [];
 
constructor(private http : HttpClient) { }

public getMemes(page : Number){
this.memes = [];
this.http.get('/memes/' + page).subscribe(
  data => this.extractMemes(data));
}

public add(fd : FormData){
  this.http.post('/add',fd).subscribe();
}

public delete(i : Number){
  this.http.delete('/delete/' + i).subscribe();
}

public editMemeSetUp(i,index)
{
 this.index = i;
 this.apiIndex = index;
}

public updateTitle(title : String){
  this.http.put('/updateTitle/' + this.apiIndex,title).subscribe();
}

public updateMeme(fd : FormData){
  this.http.put('/update/' + this.apiIndex , fd).subscribe();
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
