import { Component, OnInit } from '@angular/core';
import {MemeService} from '../meme.service';
import {Meme} from '../Models/Meme';
import {Router} from '@angular/router';
import {HttpClient} from '@angular/common/http';
@Component({
  selector: 'app-add',
  templateUrl: './add.component.html',
  styleUrls: ['./add.component.css']
})
export class AddComponent implements OnInit {
  selectedImage : File = null;
  meme : Meme = new Meme('','',0);
  edit : boolean = false;
  constructor(private router:Router, private memeService : MemeService, private http:HttpClient)
   { }

  ngOnInit() {
    if(!(this.memeService.index === -1))
    {
      this.meme = this.memeService.memes[this.memeService.index];
      this.edit = true;
    }
  }

  onConfirm(){
    const fd = new FormData();
    if(this.edit === false)
    {
    fd.append('file', this.selectedImage,this.meme.title);
    console.log(this.meme.title);
    this.memeService.add(fd);
    }

   else
   {
     if(this.selectedImage === null)
     {
      this.memeService.updateTitle(this.meme.title);
     }
     
     else
     {
         fd.append('file',this.selectedImage,this.meme.title);
         this.memeService.updateMeme(fd);
     }
   }
   setTimeout(() => 
  { this.router.navigate(['/Home']);}, 500);
  }

  handleImages(event){
    this.selectedImage = event.target.files[0];
      }

      abort(){
        this.edit = false;
        this.router.navigate(['/Home']);
      }

      ngOnDestroy(){
        this.memeService.finishEditMeme();
        this.edit = false;
      }
}
