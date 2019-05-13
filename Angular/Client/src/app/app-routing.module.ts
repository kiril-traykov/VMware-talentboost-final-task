import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ExploreComponent } from './explore/explore.component';
import {AddComponent} from './add/add.component';
import {HomeComponent} from './home/home.component';

const routes: Routes = [{path: 'Home',component:HomeComponent},
{path: 'Add',component:AddComponent},
{ path : 'Explore', component:ExploreComponent}];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
export const routingComponents = [HomeComponent,AddComponent,ExploreComponent];