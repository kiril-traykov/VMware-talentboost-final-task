import { Component, OnInit } from '@angular/core';
import {DomainService} from '../domain.service';
@Component({
  selector: 'app-explore',
  templateUrl: './explore.component.html',
  styleUrls: ['./explore.component.css']
})
export class ExploreComponent implements OnInit {

  constructor(private domainService : DomainService) { }

  ngOnInit() {
    this.domainService.getDomains();
  }

  onChange(domainName){
    this.domainService.getMemes(domainName);
  }

}
