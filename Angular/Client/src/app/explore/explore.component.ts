import { Component, OnInit } from '@angular/core';
import {DomainService} from '../domain.service';
import { Domain } from '../Models/Domain';
@Component({
  selector: 'app-explore',
  templateUrl: './explore.component.html',
  styleUrls: ['./explore.component.css']
})
export class ExploreComponent implements OnInit {
  currentDomain : String;
  filter : String;
  constructor(public domainService : DomainService) { }

  ngOnInit() {
    this.domainService.getDomains();
  }

  advancedFilter(filter : String){
    if(filter !== '')
    {
    this.domainService.advancedFilter(filter);
  }
  }

  clear(){
    this.filter = '';
    this.domainService.getMemes(this.currentDomain);
  }

  onChange(domainName){
    this.currentDomain = domainName;
    this.domainService.getMemes(this.currentDomain);
  }

}
