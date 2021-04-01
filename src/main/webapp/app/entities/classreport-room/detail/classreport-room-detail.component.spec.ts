import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ClassreportRoomDetailComponent } from './classreport-room-detail.component';

describe('Component Tests', () => {
  describe('ClassreportRoom Management Detail Component', () => {
    let comp: ClassreportRoomDetailComponent;
    let fixture: ComponentFixture<ClassreportRoomDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [ClassreportRoomDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ classreportRoom: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(ClassreportRoomDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ClassreportRoomDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load classreportRoom on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.classreportRoom).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
