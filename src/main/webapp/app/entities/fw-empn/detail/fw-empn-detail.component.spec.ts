import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FwEmpnDetailComponent } from './fw-empn-detail.component';

describe('Component Tests', () => {
  describe('FwEmpn Management Detail Component', () => {
    let comp: FwEmpnDetailComponent;
    let fixture: ComponentFixture<FwEmpnDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [FwEmpnDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ fwEmpn: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(FwEmpnDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FwEmpnDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load fwEmpn on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.fwEmpn).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
