jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { DDepotService } from '../service/d-depot.service';
import { IDDepot, DDepot } from '../d-depot.model';

import { DDepotUpdateComponent } from './d-depot-update.component';

describe('Component Tests', () => {
  describe('DDepot Management Update Component', () => {
    let comp: DDepotUpdateComponent;
    let fixture: ComponentFixture<DDepotUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let dDepotService: DDepotService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [DDepotUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(DDepotUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DDepotUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      dDepotService = TestBed.inject(DDepotService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const dDepot: IDDepot = { id: 456 };

        activatedRoute.data = of({ dDepot });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(dDepot));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const dDepot = { id: 123 };
        spyOn(dDepotService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ dDepot });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: dDepot }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(dDepotService.update).toHaveBeenCalledWith(dDepot);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const dDepot = new DDepot();
        spyOn(dDepotService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ dDepot });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: dDepot }));
        saveSubject.complete();

        // THEN
        expect(dDepotService.create).toHaveBeenCalledWith(dDepot);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const dDepot = { id: 123 };
        spyOn(dDepotService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ dDepot });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(dDepotService.update).toHaveBeenCalledWith(dDepot);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
