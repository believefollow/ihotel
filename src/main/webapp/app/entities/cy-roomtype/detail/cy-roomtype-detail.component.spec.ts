import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CyRoomtypeDetailComponent } from './cy-roomtype-detail.component';

describe('Component Tests', () => {
  describe('CyRoomtype Management Detail Component', () => {
    let comp: CyRoomtypeDetailComponent;
    let fixture: ComponentFixture<CyRoomtypeDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [CyRoomtypeDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ cyRoomtype: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(CyRoomtypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CyRoomtypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load cyRoomtype on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.cyRoomtype).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
