import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DUnitDetailComponent } from './d-unit-detail.component';

describe('Component Tests', () => {
  describe('DUnit Management Detail Component', () => {
    let comp: DUnitDetailComponent;
    let fixture: ComponentFixture<DUnitDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [DUnitDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ dUnit: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(DUnitDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DUnitDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load dUnit on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.dUnit).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
