import { OnInit } from '@angular/core';
import { Component } from '@angular/core';
import { LayoutService } from '../../services/layout/layout.service';


@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.scss'
})
export class AppMenuComponent implements OnInit {

    model: any[] = [];

    constructor(public layoutService: LayoutService) { }

    ngOnInit() {
        this.model = [
            {
                label: 'Home',
                items: [
                    { label: 'Dashboard', routerLink: ['/'] }
                ]
            },
            {
                label: 'Work',
                items: [
                    { label: 'Your Work', routerLink: ['/work'] },
                    { label: 'Projects', routerLink: ['/projects'] },
                    { label: 'Teams', routerLink: ['/team'] },
                    { label: 'Filters', routerLink: ['/filters'] }
                ]
            },
        ];
    }
}
