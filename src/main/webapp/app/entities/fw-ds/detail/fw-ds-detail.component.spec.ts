import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FwDsDetailComponent } from './fw-ds-detail.component';

describe('Component Tests', () => {
  describe('FwDs Management Detail Component', () => {
    let comp: FwDsDetailComponent;
    let fixture: ComponentFixture<FwDsDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [FwDsDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ fwDs: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(FwDsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FwDsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load fwDs on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.fwDs).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
