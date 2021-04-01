import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FwYlwpDetailComponent } from './fw-ylwp-detail.component';

describe('Component Tests', () => {
  describe('FwYlwp Management Detail Component', () => {
    let comp: FwYlwpDetailComponent;
    let fixture: ComponentFixture<FwYlwpDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [FwYlwpDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ fwYlwp: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(FwYlwpDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FwYlwpDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load fwYlwp on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.fwYlwp).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
