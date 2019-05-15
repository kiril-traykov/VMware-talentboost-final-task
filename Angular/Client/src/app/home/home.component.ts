import { Component, OnInit } from '@angular/core';
import {Meme} from '../Models/Meme';
import {MemeService} from '../meme.service';
import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router';
import { TouchSequence } from 'selenium-webdriver';
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
 currentPage : number = 1;
 totalPages;
 contentPerPage : number = 4;
 filterstr;

  constructor(public memeService : MemeService,private http : HttpClient,
    private router : Router) { }

  ngOnInit() {
    this.setUpPagination();
    this.getMemes();
  }

  getMemes(){
    setTimeout(() => this.memeService.getMemes(this.currentPage),500);
  }

  onDelete(i){
    this.memeService.delete(i);
    window.location.reload();
  }

  onEdit(i,index){
    this.memeService.editMemeSetUp(i,index);
    this.router.navigate(['/Add'])
  }

  setUpPagination()
  {
    this.currentPage = 1;
   this.http.get('/page/' + this.contentPerPage).subscribe(
     data => this.totalPages = data
   )
  }

  filter(filter : String){
  if(filter === '')
  {
    setTimeout (() => this.http.post('/filter',"RemoveFilterFromApi").subscribe(), 300);
    setTimeout (() => this.setUpPagination(), 300);
    this.getMemes();
  }
   else
   { 
    setTimeout (() => this.http.post('/filter',filter).subscribe(), 300);
    setTimeout (() => this.setUpPagination(), 300);
    this.getMemes();
  }
}

  setPageContent(event){
    this.contentPerPage = event.target.value;
    this.currentPage = 1;
    setTimeout (() => this.setUpPagination(), 300);
    this.getMemes();
  }
  
  next(){
    this.currentPage++;
    setTimeout(() => this.getMemes(),300);
  }

  previous(){
    this.currentPage--;
    setTimeout(() => this.getMemes(),300);
  }

}
