import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FwJywpDetailComponent } from './fw-jywp-detail.component';

describe('Component Tests', () => {
  describe('FwJywp Management Detail Component', () => {
    let comp: FwJywpDetailComponent;
    let fixture: ComponentFixture<FwJywpDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [FwJywpDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ fwJywp: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(FwJywpDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FwJywpDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load fwJywp on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.fwJywp).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
