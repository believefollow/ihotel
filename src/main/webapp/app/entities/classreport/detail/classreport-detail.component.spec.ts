import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ClassreportDetailComponent } from './classreport-detail.component';

describe('Component Tests', () => {
  describe('Classreport Management Detail Component', () => {
    let comp: ClassreportDetailComponent;
    let fixture: ComponentFixture<ClassreportDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [ClassreportDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ classreport: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(ClassreportDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ClassreportDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load classreport on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.classreport).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
